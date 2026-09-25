package com.butembo.alertgeste.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.butembo.alertgeste.data.local.entity.SmsPart
import com.butembo.alertgeste.data.local.dao.AlerteDao
import com.butembo.alertgeste.data.local.dao.ContactDao
import com.butembo.alertgeste.data.local.dao.GesteProfilDao
import com.butembo.alertgeste.data.local.dao.UtilisateurDao
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur

@Database(
    entities = [Utilisateur::class, Contact::class, GesteProfil::class, Alerte::class, SmsPart::class],
    version = 2,
    exportSchema = true
)
abstract class AlertGesteDatabase : RoomDatabase() {
    abstract fun utilisateurDao(): UtilisateurDao
    abstract fun contactDao(): ContactDao
    abstract fun gesteProfilDao(): GesteProfilDao
    abstract fun alerteDao(): AlerteDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE alertes ADD COLUMN detail TEXT NOT NULL DEFAULT ''")
                db.execSQL("CREATE TABLE IF NOT EXISTS sms_parts (id TEXT NOT NULL PRIMARY KEY, alerteId INTEGER NOT NULL, contactId INTEGER NOT NULL, nom TEXT NOT NULL, telephone TEXT NOT NULL, partIndex INTEGER NOT NULL, statut TEXT NOT NULL, FOREIGN KEY(alerteId) REFERENCES alertes(id) ON DELETE CASCADE)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_sms_parts_alerteId ON sms_parts(alerteId)")
                db.execSQL("DELETE FROM utilisateurs WHERE id NOT IN (SELECT MIN(id) FROM utilisateurs)")
                db.execSQL("DELETE FROM geste_profils WHERE id NOT IN (SELECT MAX(id) FROM geste_profils)")
                // Existing installations must explicitly enable the corrected surveillance.
                db.execSQL("UPDATE utilisateurs SET surveillanceActive = 0")
                db.execSQL("UPDATE alertes SET detail = 'Ancienne version : résultat SMS non vérifié.' WHERE statut = 'ENVOYEE'")
            }
        }
    }
}
