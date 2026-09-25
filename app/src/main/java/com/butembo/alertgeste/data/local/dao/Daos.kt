package com.butembo.alertgeste.data.local.dao

import androidx.room.*
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur
import com.butembo.alertgeste.data.local.entity.SmsPart
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilisateurDao {
    @Query("SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1")
    fun getUtilisateur(): Flow<Utilisateur?>

    @Query("SELECT * FROM utilisateurs ORDER BY id ASC LIMIT 1")
    suspend fun getUtilisateurOnce(): Utilisateur?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtilisateur(utilisateur: Utilisateur)

    @Update
    suspend fun updateUtilisateur(utilisateur: Utilisateur)

    @Query("DELETE FROM utilisateurs")
    suspend fun clearUtilisateurs()

    @Query("UPDATE utilisateurs SET surveillanceActive = :active")
    suspend fun setSurveillance(active: Boolean)
}

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY nom ASC")
    fun getTousLesContacts(): Flow<List<Contact>>

    @Query("SELECT * FROM contacts ORDER BY nom ASC")
    suspend fun getTousLesContactsOnce(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("DELETE FROM contacts")
    suspend fun clearContacts()

    @Query("SELECT COUNT(*) FROM contacts WHERE telephone = :phone AND id != :exceptId")
    suspend fun countPhone(phone: String, exceptId: Long): Int
}

@Dao
interface GesteProfilDao {
    @Query("SELECT * FROM geste_profils ORDER BY id DESC LIMIT 1")
    fun getProfilActif(): Flow<GesteProfil?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfil(profil: GesteProfil)

    @Query("DELETE FROM geste_profils")
    suspend fun clearProfils()
}

@Dao
interface AlerteDao {
    @Query("SELECT * FROM alertes ORDER BY horodatage DESC LIMIT 200")
    fun getHistoriqueAlertes(): Flow<List<Alerte>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerte(alerte: Alerte): Long

    @Query("SELECT * FROM alertes WHERE id = :id")
    suspend fun getAlerte(id: Long): Alerte?

    @Query("SELECT * FROM alertes WHERE id = :id")
    fun observeAlerte(id: Long): Flow<Alerte?>

    @Query("UPDATE alertes SET statut = :status, detail = :detail WHERE id = :id")
    suspend fun setStatus(id: Long, status: String, detail: String)

    @Query("UPDATE alertes SET latitude = :latitude, longitude = :longitude WHERE id = :id")
    suspend fun setLocation(id: Long, latitude: Double?, longitude: Double?)

    @Query("UPDATE alertes SET contactsNotifies = :contacts WHERE id = :id")
    suspend fun setContacts(id: Long, contacts: String)

    @Insert
    suspend fun insertParts(parts: List<SmsPart>)

    @Query("SELECT * FROM sms_parts WHERE alerteId = :alertId")
    suspend fun getParts(alertId: Long): List<SmsPart>

    @Query("SELECT * FROM sms_parts WHERE id = :id")
    suspend fun getPart(id: String): SmsPart?

    @Query("UPDATE sms_parts SET statut = :status, failureReason = :failureReason WHERE id = :id AND statut IN ('EN_ATTENTE', 'INCONNU')")
    suspend fun setPartStatus(id: String, status: String, failureReason: String)

    @Query("UPDATE sms_parts SET statut = 'INCONNU' WHERE alerteId = :alertId AND statut = 'EN_ATTENTE'")
    suspend fun expireParts(alertId: Long)

    @Query("UPDATE alertes SET statut = 'INTERROMPUE', detail = 'Service interrompu avant envoi.' WHERE statut IN ('COMPTE_A_REBOURS', 'LOCALISATION')")
    suspend fun interruptPreparations()

    @Query("SELECT id FROM alertes WHERE statut = 'EN_COURS'")
    suspend fun pendingAlerts(): List<Long>

    @Query("DELETE FROM alertes WHERE id NOT IN (SELECT id FROM alertes ORDER BY horodatage DESC LIMIT 200) AND statut NOT IN ('COMPTE_A_REBOURS', 'LOCALISATION', 'EN_COURS')")
    suspend fun pruneHistory()

    @Query("DELETE FROM alertes WHERE id = :id AND statut NOT IN ('COMPTE_A_REBOURS', 'LOCALISATION', 'EN_COURS')")
    suspend fun deleteCompletedAlerte(id: Long): Int

    @Query("DELETE FROM alertes")
    suspend fun clearHistorique()
}
