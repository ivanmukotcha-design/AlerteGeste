package com.butembo.alertgeste.data.local.dao

import androidx.room.*
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur
import kotlinx.coroutines.flow.Flow

@Dao
interface UtilisateurDao {
    @Query("SELECT * FROM utilisateurs LIMIT 1")
    fun getUtilisateur(): Flow<Utilisateur?>

    @Query("SELECT * FROM utilisateurs LIMIT 1")
    suspend fun getUtilisateurOnce(): Utilisateur?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUtilisateur(utilisateur: Utilisateur)

    @Update
    suspend fun updateUtilisateur(utilisateur: Utilisateur)

    @Query("DELETE FROM utilisateurs")
    suspend fun clearUtilisateurs()
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
}

@Dao
interface GesteProfilDao {
    @Query("SELECT * FROM geste_profils LIMIT 1")
    fun getProfilActif(): Flow<GesteProfil?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfil(profil: GesteProfil)

    @Query("DELETE FROM geste_profils")
    suspend fun clearProfils()
}

@Dao
interface AlerteDao {
    @Query("SELECT * FROM alertes ORDER BY horodatage DESC")
    fun getHistoriqueAlertes(): Flow<List<Alerte>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerte(alerte: Alerte)

    @Delete
    suspend fun deleteAlerte(alerte: Alerte)

    @Query("DELETE FROM alertes")
    suspend fun clearHistorique()
}
