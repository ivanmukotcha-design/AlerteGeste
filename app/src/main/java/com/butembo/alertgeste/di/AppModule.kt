package com.butembo.alertgeste.di

import android.content.Context
import androidx.room.Room
import com.butembo.alertgeste.data.local.AlertGesteDatabase
import com.butembo.alertgeste.data.local.dao.AlerteDao
import com.butembo.alertgeste.data.local.dao.ContactDao
import com.butembo.alertgeste.data.local.dao.GesteProfilDao
import com.butembo.alertgeste.data.local.dao.UtilisateurDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AlertGesteDatabase {
        return Room.databaseBuilder(
            context,
            AlertGesteDatabase::class.java,
            "alertgeste_db"
        ).addMigrations(AlertGesteDatabase.MIGRATION_1_2).build()
    }

    @Provides
    fun provideUtilisateurDao(db: AlertGesteDatabase): UtilisateurDao = db.utilisateurDao()

    @Provides
    fun provideContactDao(db: AlertGesteDatabase): ContactDao = db.contactDao()

    @Provides
    fun provideGesteProfilDao(db: AlertGesteDatabase): GesteProfilDao = db.gesteProfilDao()

    @Provides
    fun provideAlerteDao(db: AlertGesteDatabase): AlerteDao = db.alerteDao()
}
