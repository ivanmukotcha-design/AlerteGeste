package com.butembo.alertgeste.data.repository

import androidx.room.withTransaction
import com.butembo.alertgeste.data.local.AlertGesteDatabase
import com.butembo.alertgeste.data.local.entity.*
import com.butembo.alertgeste.domain.AlertStatus
import com.butembo.alertgeste.domain.InputRules
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertGesteRepository @Inject constructor(private val db: AlertGesteDatabase) {
    private val users get() = db.utilisateurDao()
    private val contacts get() = db.contactDao()
    private val profiles get() = db.gesteProfilDao()
    private val alerts get() = db.alerteDao()

    fun getUtilisateur() = users.getUtilisateur()
    suspend fun getUtilisateurOnce() = users.getUtilisateurOnce()
    suspend fun enregistrerUtilisateur(user: Utilisateur) = db.withTransaction {
        require(InputRules.validProfile(user.nom, user.telephone, user.messageAlerte)) { "Nom, numéro (7 à 15 chiffres) et message (1 à 480 caractères) requis." }
        val existing = users.getUtilisateurOnce()
        users.insertUtilisateur(user.copy(id = existing?.id ?: 1, nom = user.nom.trim(),
            telephone = InputRules.phone(user.telephone)!!, messageAlerte = user.messageAlerte.trim(),
            surveillanceActive = existing?.surveillanceActive ?: false))
    }
    suspend fun updateUtilisateur(user: Utilisateur) = enregistrerUtilisateur(user)
    suspend fun sauvegarderUtilisateur(user: Utilisateur) = enregistrerUtilisateur(user)
    suspend fun mettreAJourUtilisateur(user: Utilisateur) = enregistrerUtilisateur(user)
    suspend fun setSurveillance(active: Boolean) = users.setSurveillance(active)

    fun getTousLesContacts() = contacts.getTousLesContacts()
    fun getTousContacts() = getTousLesContacts()
    suspend fun getTousContactsOnce() = contacts.getTousLesContactsOnce()
    suspend fun ajouterContact(contact: Contact) = saveContact(contact)
    suspend fun mettreAJourContact(contact: Contact) = saveContact(contact)
    private suspend fun saveContact(contact: Contact) = db.withTransaction {
        val phone = InputRules.phone(contact.telephone)
        require(contact.nom.trim().length in 1..80 && phone != null) { "Nom et numéro valide (7 à 15 chiffres) requis." }
        require(contacts.countPhone(phone, contact.id) == 0) { "Ce numéro est déjà enregistré." }
        require(contact.id != 0L || contacts.getTousLesContactsOnce().size < 10) { "Dix contacts maximum pour limiter le délai et le coût des SMS." }
        val normalized = contact.copy(nom = contact.nom.trim(), telephone = phone, relation = contact.relation.trim().take(80))
        if (contact.id == 0L) contacts.insertContact(normalized) else contacts.updateContact(normalized)
    }
    suspend fun supprimerContact(contact: Contact) = contacts.deleteContact(contact)

    fun getProfilActif() = profiles.getProfilActif()
    suspend fun enregistrerProfil(profile: GesteProfil) = db.withTransaction {
        require(profile.seuilMin.isFinite() && profile.seuilMin in 3f..30f)
        profiles.clearProfils()
        require(profile.fenetreTempsMs in 1500L..3000L && profile.nbRepetitions == 3)
        profiles.insertProfil(profile.copy(id = 1, estEnregistre = true))
    }
    suspend fun sauvegarderProfil(profile: GesteProfil) = enregistrerProfil(profile)

    fun getHistoriqueAlertes() = alerts.getHistoriqueAlertes()
    fun getToutesAlertes() = getHistoriqueAlertes()
    fun observeAlerte(id: Long) = alerts.observeAlerte(id)
    suspend fun getAlerte(id: Long) = alerts.getAlerte(id)
    suspend fun enregistrerAlerte(alert: Alerte): Long = db.withTransaction {
        val id = alerts.insertAlerte(alert)
        alerts.pruneHistory()
        id
    }
    suspend fun setAlertStatus(id: Long, status: String, detail: String = "") = alerts.setStatus(id, status, detail)
    suspend fun setLocation(id: Long, latitude: Double?, longitude: Double?) = alerts.setLocation(id, latitude, longitude)
    suspend fun prepareParts(id: Long, parts: List<SmsPart>) = db.withTransaction {
        alerts.insertParts(parts)
        alerts.setStatus(id, AlertStatus.SENDING, "En attente de confirmation de l’opérateur.")
    }
    suspend fun recordPart(id: String, status: String): Alerte? = db.withTransaction {
        val part = alerts.getPart(id) ?: return@withTransaction null
        alerts.setPartStatus(id, status)
        refreshResult(part.alerteId)
    }
    private suspend fun refreshResult(id: Long): Alerte? {
        val parts = alerts.getParts(id)
        if (parts.isEmpty()) return alerts.getAlerte(id)
        val groups = parts.groupBy { it.contactId }.values
        val sent = groups.filter { group -> group.all { it.statut == AlertStatus.SENT } }
        val status = AlertStatus.aggregate(parts.map { it.statut })
        alerts.setContacts(id, sent.joinToString(", ") { it.first().nom })
        alerts.setStatus(id, status, "${sent.size}/${groups.size} destinataire(s) : toutes les parties confirmées par l’opérateur. Réception non confirmée.")
        return alerts.getAlerte(id)
    }
    suspend fun expireAlert(id: Long): Alerte? = db.withTransaction {
        alerts.expireParts(id)
        refreshResult(id)
    }
    suspend fun recoverInterrupted() = db.withTransaction {
        alerts.interruptPreparations()
        alerts.pendingAlerts().forEach { alerts.expireParts(it); refreshResult(it) }
    }
    suspend fun supprimerAlerte(alert: Alerte): Boolean = alerts.deleteCompletedAlerte(alert.id) == 1
    suspend fun effacerHistorique() = alerts.clearHistorique()
    suspend fun reinitialiserCompte() = db.withTransaction {
        users.clearUtilisateurs()
        contacts.clearContacts()
        profiles.clearProfils()
        alerts.clearHistorique()
    }
}
