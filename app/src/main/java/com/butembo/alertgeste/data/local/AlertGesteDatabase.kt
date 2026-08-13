package com.butembo.alertgeste.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.butembo.alertgeste.data.local.dao.AlerteDao
import com.butembo.alertgeste.data.local.dao.ContactDao
import com.butembo.alertgeste.data.local.dao.GesteProfilDao
import com.butembo.alertgeste.data.local.dao.UtilisateurDao
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur

@Database(
    entities = [Utilisateur::class, Contact::class, GesteProfil::class, Alerte::class],
    version = 1,
    exportSchema = false
)
abstract class AlertGesteDatabase : RoomDatabase() {
    abstract fun utilisateurDao(): UtilisateurDao
    abstract fun contactDao(): ContactDao
    abstract fun gesteProfilDao(): GesteProfilDao
    abstract fun alerteDao(): AlerteDao
}
