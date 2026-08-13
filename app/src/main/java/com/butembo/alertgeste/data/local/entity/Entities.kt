package com.butembo.alertgeste.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "utilisateurs")
data class Utilisateur(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nom: String,
    val telephone: String,
    val messageAlerte: String,
    val surveillanceActive: Boolean = false
)

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nom: String,
    val telephone: String,
    val relation: String
)

@Entity(tableName = "geste_profils")
data class GesteProfil(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val seuilMin: Float,
    val seuilMax: Float,
    val axeDetection: String = "resultante",
    val fenetreTempsMs: Long = 1500L,
    val nbRepetitions: Int = 3,
    val estEnregistre: Boolean = false
)

@Entity(tableName = "alertes")
data class Alerte(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val horodatage: Long = System.currentTimeMillis(),
    val latitude: Double?,
    val longitude: Double?,
    val statut: String, // "ENVOYEE", "ANNULEE", "ECHEC"
    val contactsNotifies: String // JSON ou liste séparée par virgules
)
