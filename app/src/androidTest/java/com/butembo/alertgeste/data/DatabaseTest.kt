package com.butembo.alertgeste.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.butembo.alertgeste.data.local.AlertGesteDatabase
import com.butembo.alertgeste.data.local.entity.*
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.domain.AlertStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test fun recalibrationReplacesProfileAndResetRemovesAllData() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AlertGesteDatabase::class.java).build()
        try {
            val repo = AlertGesteRepository(db)
            repo.enregistrerUtilisateur(Utilisateur(nom = "Alice", telephone = "+243812345678", messageAlerte = "Au secours"))
            repo.enregistrerUtilisateur(Utilisateur(nom = "Bob", telephone = "+243812345678", messageAlerte = "Au secours"))
            repo.sauvegarderProfil(GesteProfil(seuilMin = 5f, seuilMax = 20f))
            repo.sauvegarderProfil(GesteProfil(seuilMin = 8f, seuilMax = 25f, fenetreTempsMs = 2500))
            assertEquals(8f, repo.getProfilActif().first()!!.seuilMin, 0.01f)
            assertEquals(2500L, repo.getProfilActif().first()!!.fenetreTempsMs)
            assertEquals("Bob", repo.getUtilisateurOnce()!!.nom)
            repo.ajouterContact(Contact(nom = "Contact", telephone = "+243812345679", relation = "Ami"))
            repo.reinitialiserCompte()
            assertNull(repo.getUtilisateurOnce())
            assertNull(repo.getProfilActif().first())
            assertTrue(repo.getTousContactsOnce().isEmpty())
            assertTrue(repo.getHistoriqueAlertes().first().isEmpty())
        } finally { db.close() }
    }

    @Test fun callbacksAreIdempotentAndLateResultCanResolveUnknown() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AlertGesteDatabase::class.java).build()
        try {
            val repo = AlertGesteRepository(db)
            val id = repo.enregistrerAlerte(Alerte(latitude = null, longitude = null, statut = AlertStatus.COUNTDOWN, contactsNotifies = ""))
            repo.prepareParts(id, listOf(SmsPart("a", id, 1, "Alice", "123456789", 0), SmsPart("b", id, 1, "Alice", "123456789", 1)))
            repo.recordPart("a", AlertStatus.SENT)
            repo.recordPart("a", AlertStatus.FAILED) // duplicate must not overwrite a terminal result
            assertEquals(AlertStatus.SENDING, repo.getAlerte(id)!!.statut)
            repo.expireAlert(id)
            assertEquals(AlertStatus.UNKNOWN, repo.getAlerte(id)!!.statut)
            repo.recordPart("b", AlertStatus.SENT)
            assertEquals(AlertStatus.SENT, repo.getAlerte(id)!!.statut)
            repo.effacerHistorique()
            assertNull(repo.recordPart("b", AlertStatus.SENT))
        } finally { db.close() }
    }

    @Test fun deletingHistoryRemovesOnlySelectedAlertAndItsParts() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AlertGesteDatabase::class.java).build()
        try {
            val repo = AlertGesteRepository(db)
            val sample = Alerte(latitude = null, longitude = null, statut = AlertStatus.COUNTDOWN, contactsNotifies = "")
            val selected = repo.enregistrerAlerte(sample)
            val retained = repo.enregistrerAlerte(sample)
            repo.prepareParts(selected, listOf(SmsPart("selected", selected, 1, "Test", "123456789", 0)))
            repo.prepareParts(retained, listOf(SmsPart("retained", retained, 1, "Test", "123456789", 0)))
            repo.expireAlert(selected)
            val displayed = repo.getAlerte(selected)!!

            assertTrue(repo.supprimerAlerte(displayed))
            assertNull(repo.getAlerte(selected))
            assertTrue(db.alerteDao().getParts(selected).isEmpty())
            assertNotNull(repo.getAlerte(retained))
            assertEquals(1, db.alerteDao().getParts(retained).size)
            assertEquals(listOf(retained), repo.getHistoriqueAlertes().first().map { it.id })
            assertNull(repo.recordPart("selected", AlertStatus.SENT))
            assertFalse(repo.supprimerAlerte(displayed))

            repo.recordPart("retained", AlertStatus.SENT)
            assertTrue(repo.supprimerAlerte(repo.getAlerte(retained)!!))
            assertTrue(repo.getHistoriqueAlertes().first().isEmpty())
        } finally { db.close() }
    }

    @Test fun deletingHistoryChecksCurrentDatabaseStatusEvenForStaleSelection() = runBlocking {
        val db = Room.inMemoryDatabaseBuilder(context, AlertGesteDatabase::class.java).build()
        try {
            val repo = AlertGesteRepository(db)
            val id = repo.enregistrerAlerte(Alerte(latitude = null, longitude = null, statut = AlertStatus.FAILED, contactsNotifies = ""))
            val staleSelection = repo.getAlerte(id)!!
            for (status in listOf(AlertStatus.COUNTDOWN, AlertStatus.LOCATING, AlertStatus.SENDING)) {
                repo.setAlertStatus(id, status)
                assertFalse(repo.supprimerAlerte(staleSelection))
                assertEquals(status, repo.getAlerte(id)!!.statut)
            }
            repo.setAlertStatus(id, AlertStatus.CANCELLED)
            assertTrue(repo.supprimerAlerte(staleSelection))
        } finally { db.close() }
    }

    @Test fun migrationPreservesV1DataAndSelectsLatestCalibration() = runBlocking {
        val name = "migration-test.db"
        context.deleteDatabase(name)
        val path = context.getDatabasePath(name)
        path.parentFile!!.mkdirs()
        SQLiteDatabase.openOrCreateDatabase(path, null).use { old ->
            old.execSQL("CREATE TABLE utilisateurs (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nom TEXT NOT NULL, telephone TEXT NOT NULL, messageAlerte TEXT NOT NULL, surveillanceActive INTEGER NOT NULL)")
            old.execSQL("CREATE TABLE contacts (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nom TEXT NOT NULL, telephone TEXT NOT NULL, relation TEXT NOT NULL)")
            old.execSQL("CREATE TABLE geste_profils (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, seuilMin REAL NOT NULL, seuilMax REAL NOT NULL, axeDetection TEXT NOT NULL, fenetreTempsMs INTEGER NOT NULL, nbRepetitions INTEGER NOT NULL, estEnregistre INTEGER NOT NULL)")
            old.execSQL("CREATE TABLE alertes (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, horodatage INTEGER NOT NULL, latitude REAL, longitude REAL, statut TEXT NOT NULL, contactsNotifies TEXT NOT NULL)")
            old.execSQL("INSERT INTO utilisateurs VALUES (1,'Alice','123456789','Au secours',1)")
            old.execSQL("INSERT INTO utilisateurs VALUES (2,'Doublon','123456789','Au secours',0)")
            old.execSQL("INSERT INTO contacts VALUES (1,'Bob','987654321','Ami')")
            old.execSQL("INSERT INTO geste_profils VALUES (1,5,20,'resultante',1500,3,1)")
            old.execSQL("INSERT INTO geste_profils VALUES (2,8,25,'resultante',1500,3,1)")
            old.execSQL("INSERT INTO alertes VALUES (1,1234,NULL,NULL,'ENVOYEE','Bob')")
            old.version = 1
        }
        val db = Room.databaseBuilder(context, AlertGesteDatabase::class.java, name)
            .addMigrations(AlertGesteDatabase.MIGRATION_1_2).build()
        try {
            val repo = AlertGesteRepository(db)
            assertEquals("Alice", repo.getUtilisateurOnce()!!.nom)
            assertFalse(repo.getUtilisateurOnce()!!.surveillanceActive)
            assertEquals(8f, repo.getProfilActif().first()!!.seuilMin, 0.01f)
            assertEquals(1, repo.getTousContactsOnce().size)
            assertTrue(repo.getHistoriqueAlertes().first().single().detail.contains("non vérifié"))
        } finally { db.close(); context.deleteDatabase(name) }
    }
}
