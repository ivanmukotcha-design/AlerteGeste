package com.butembo.alertgeste.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.Index

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
    val contactsNotifies: String,
    @ColumnInfo(defaultValue = "''") val detail: String = ""
)

@Entity(
    tableName = "sms_parts",
    foreignKeys = [ForeignKey(entity = Alerte::class, parentColumns = ["id"], childColumns = ["alerteId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("alerteId")]
)
data class SmsPart(
    @PrimaryKey val id: String,
    val alerteId: Long,
    val contactId: Long,
    val nom: String,
    val telephone: String,
    val partIndex: Int,
    val statut: String = "EN_ATTENTE"
)
