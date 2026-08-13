package com.butembo.alertgeste.data.repository

import com.butembo.alertgeste.data.local.dao.AlerteDao
import com.butembo.alertgeste.data.local.dao.ContactDao
import com.butembo.alertgeste.data.local.dao.GesteProfilDao
import com.butembo.alertgeste.data.local.dao.UtilisateurDao
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlertGesteRepository @Inject constructor(
    private val utilisateurDao: UtilisateurDao,
    private val contactDao: ContactDao,
    private val gesteProfilDao: GesteProfilDao,
    private val alerteDao: AlerteDao
) {
    // Utilisateur
    fun getUtilisateur(): Flow<Utilisateur?> = utilisateurDao.getUtilisateur()
    suspend fun getUtilisateurOnce(): Utilisateur? = utilisateurDao.getUtilisateurOnce()
    suspend fun enregistrerUtilisateur(utilisateur: Utilisateur) = utilisateurDao.insertUtilisateur(utilisateur)
    suspend fun updateUtilisateur(utilisateur: Utilisateur) = utilisateurDao.updateUtilisateur(utilisateur)
    // Aliases for ViewModels
    suspend fun sauvegarderUtilisateur(utilisateur: Utilisateur) = enregistrerUtilisateur(utilisateur)
    suspend fun mettreAJourUtilisateur(utilisateur: Utilisateur) = updateUtilisateur(utilisateur)

    // Contacts
    fun getTousLesContacts(): Flow<List<Contact>> = contactDao.getTousLesContacts()
    fun getTousContacts(): Flow<List<Contact>> = getTousLesContacts() // Alias
    suspend fun getTousContactsOnce(): List<Contact> = contactDao.getTousLesContactsOnce()
    suspend fun ajouterContact(contact: Contact) = contactDao.insertContact(contact)
    suspend fun supprimerContact(contact: Contact) = contactDao.deleteContact(contact)
    suspend fun mettreAJourContact(contact: Contact) = contactDao.updateContact(contact)

    // Profil Geste
    fun getProfilActif(): Flow<GesteProfil?> = gesteProfilDao.getProfilActif()
    suspend fun enregistrerProfil(profil: GesteProfil) = gesteProfilDao.insertProfil(profil)
    suspend fun sauvegarderProfil(profil: GesteProfil) = enregistrerProfil(profil) // Alias

    // Alertes
    fun getHistoriqueAlertes(): Flow<List<Alerte>> = alerteDao.getHistoriqueAlertes()
    fun getToutesAlertes(): Flow<List<Alerte>> = getHistoriqueAlertes() // Alias
    suspend fun enregistrerAlerte(alerte: Alerte) = alerteDao.insertAlerte(alerte)
    suspend fun supprimerAlerte(alerte: Alerte) = alerteDao.deleteAlerte(alerte)
    suspend fun effacerHistorique() = alerteDao.clearHistorique()
    
    // Reset
    suspend fun reinitialiserCompte() {
        utilisateurDao.clearUtilisateurs()
        contactDao.clearContacts()
        gesteProfilDao.clearProfils()
        alerteDao.clearHistorique()
    }
}
