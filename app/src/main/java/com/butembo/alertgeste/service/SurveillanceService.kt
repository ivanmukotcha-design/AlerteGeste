package com.butembo.alertgeste.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.location.Location
import android.os.*
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.butembo.alertgeste.AlertGesteApp.Companion.CHANNEL_ALERTE
import com.butembo.alertgeste.AlertGesteApp.Companion.CHANNEL_SURVEILLANCE
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.receiver.AlerteActionReceiver
import com.butembo.alertgeste.ui.MainActivity
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.math.sqrt

@AndroidEntryPoint
class SurveillanceService : LifecycleService(), SensorEventListener {

    @Inject lateinit var repository: AlertGesteRepository

    private lateinit var sensorManager: SensorManager
    private var accelerometre: Sensor? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // État détection geste
    private var profil: GesteProfil? = null
    private val timestampsDetection = mutableListOf<Long>()
    private var alerteEnCours = false
    private var countdownJob: Job? = null

    // Filtre passe-bas
    private val alpha = 0.8f
    private var gravite = FloatArray(3) { 0f }

    companion object {
        const val ACTION_DEMARRER = "ACTION_DEMARRER"
        const val ACTION_ARRETER  = "ACTION_ARRETER"
        const val ACTION_SOS_MANUEL = "ACTION_SOS_MANUEL"
        const val NOTIF_ID_SERVICE = 1
        const val NOTIF_ID_ALERTE  = 2
        const val ACTION_ANNULER_ALERTE = "ACTION_ANNULER_ALERTE"

        fun demarrer(context: Context) {
            val intent = Intent(context, SurveillanceService::class.java).apply {
                action = ACTION_DEMARRER
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun arreter(context: Context) {
            val intent = Intent(context, SurveillanceService::class.java).apply {
                action = ACTION_ARRETER
            }
            context.startService(intent)
        }

        fun declencherSos(context: Context) {
            val intent = Intent(context, SurveillanceService::class.java).apply {
                action = ACTION_SOS_MANUEL
            }
            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Observer le profil en temps réel
        lifecycleScope.launch {
            repository.getProfilActif().collect { p ->
                profil = p
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_ARRETER -> {
                stopSelf()
                return START_NOT_STICKY
            }
            ACTION_ANNULER_ALERTE -> {
                annulerAlerte()
                return START_STICKY
            }
            ACTION_SOS_MANUEL -> {
                if (!alerteEnCours) {
                    declencherAlerte()
                }
                return START_STICKY
            }
        }
        demarrerForeground()
        enregistrerCapteur()
        return START_STICKY
    }

    private fun demarrerForeground() {
        val notifIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notifIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notif = NotificationCompat.Builder(this, CHANNEL_SURVEILLANCE)
            .setContentTitle("AlertGeste actif")
            .setContentText("Surveillance en cours…")
            .setSmallIcon(R.drawable.ic_shield)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        startForeground(NOTIF_ID_SERVICE, notif)
    }

    private fun enregistrerCapteur() {
        accelerometre?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    // ── SensorEventListener ───────────────────────────────────────

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return
        if (alerteEnCours) return
        val p = profil ?: return

        // Filtre passe-bas pour isoler la gravité
        gravite[0] = alpha * gravite[0] + (1 - alpha) * event.values[0]
        gravite[1] = alpha * gravite[1] + (1 - alpha) * event.values[1]
        gravite[2] = alpha * gravite[2] + (1 - alpha) * event.values[2]

        val lx = event.values[0] - gravite[0]
        val ly = event.values[1] - gravite[1]
        val lz = event.values[2] - gravite[2]

        val magnitude = sqrt(lx * lx + ly * ly + lz * lz)

        if (magnitude in p.seuilMin..p.seuilMax) {
            val now = System.currentTimeMillis()
            timestampsDetection.add(now)

            // Nettoyer les timestamps hors fenêtre
            val fenetre = p.fenetreTempsMs
            timestampsDetection.removeAll { now - it > fenetre }

            if (timestampsDetection.size >= p.nbRepetitions) {
                timestampsDetection.clear()
                declencherAlerte()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    // ── Déclenchement alerte ──────────────────────────────────────

    private fun declencherAlerte() {
        alerteEnCours = true
        vibrer()
        afficherNotifComptArebours()

        countdownJob = lifecycleScope.launch {
            delay(5000L)
            if (alerteEnCours) {
                envoyerAlerte()
            }
        }
    }

    private fun annulerAlerte() {
        if (!alerteEnCours) return
        countdownJob?.cancel()
        alerteEnCours = false
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(NOTIF_ID_ALERTE)

        lifecycleScope.launch {
            repository.enregistrerAlerte(
                Alerte(
                    latitude = null,
                    longitude = null,
                    statut = "ANNULEE",
                    contactsNotifies = ""
                )
            )
        }
    }

    private fun afficherNotifComptArebours() {
        val annulerIntent = Intent(this, AlerteActionReceiver::class.java).apply {
            action = ACTION_ANNULER_ALERTE
        }
        val annulerPending = PendingIntent.getBroadcast(
            this, 0, annulerIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notif = NotificationCompat.Builder(this, CHANNEL_ALERTE)
            .setContentTitle("⚠️ ALERTE DÉCLENCHÉE")
            .setContentText("Envoi dans 5 secondes. Appuyez pour annuler.")
            .setSmallIcon(R.drawable.ic_warning)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(R.drawable.ic_cancel, "ANNULER", annulerPending)
            .setAutoCancel(false)
            .setOngoing(true)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIF_ID_ALERTE, notif)
    }

    private fun envoyerAlerte() {
        lifecycleScope.launch {
            try {
                val utilisateur = repository.getUtilisateurOnce() ?: return@launch
                val contacts = repository.getTousContactsOnce()
                if (contacts.isEmpty()) {
                    enregistrerEchecAlerte("Aucun contact de confiance")
                    return@launch
                }

                // Obtenir localisation
                val location = obtenirLocalisation()
                val lien = if (location != null) {
                    "https://maps.google.com/?q=${location.latitude},${location.longitude}"
                } else ""

                val message = buildString {
                    append(utilisateur.messageAlerte)
                    if (lien.isNotEmpty()) {
                        append("\nLocalisation : $lien")
                    }
                    append("\n— ${utilisateur.nom} (${utilisateur.telephone})")
                }

                val contactsNotifies = mutableListOf<String>()
                val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    getSystemService(SmsManager::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    SmsManager.getDefault()
                }

                for (contact in contacts) {
                    try {
                        // Découper si message long
                        val parts = smsManager.divideMessage(message)
                        smsManager.sendMultipartTextMessage(
                            contact.telephone, null, parts, null, null
                        )
                        contactsNotifies.add("${contact.nom}(${contact.telephone})")
                    } catch (e: Exception) {
                        Log.e("SurveillanceService", "Erreur SMS vers ${contact.telephone}: ${e.message}")
                    }
                }

                repository.enregistrerAlerte(
                    Alerte(
                        latitude = location?.latitude,
                        longitude = location?.longitude,
                        statut = "ENVOYEE",
                        contactsNotifies = contactsNotifies.joinToString(", ")
                    )
                )

                // Mettre à jour la notification
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.cancel(NOTIF_ID_ALERTE)

                val notifEnvoyee = NotificationCompat.Builder(this@SurveillanceService, CHANNEL_ALERTE)
                    .setContentTitle("✅ Alerte envoyée")
                    .setContentText("${contactsNotifies.size} contact(s) notifié(s)")
                    .setSmallIcon(R.drawable.ic_check)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .build()
                manager.notify(NOTIF_ID_ALERTE + 1, notifEnvoyee)

            } catch (e: Exception) {
                Log.e("SurveillanceService", "Erreur envoi alerte: ${e.message}")
                enregistrerEchecAlerte(e.message ?: "Erreur inconnue")
            } finally {
                alerteEnCours = false
            }
        }
    }

    private suspend fun obtenirLocalisation(): Location? {
        return suspendCancellableCoroutine { cont ->
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location -> cont.resume(location) {} }
                    .addOnFailureListener { cont.resume(null) {} }
            } catch (e: SecurityException) {
                cont.resume(null) {}
            }
        }
    }

    private suspend fun enregistrerEchecAlerte(raison: String) {
        repository.enregistrerAlerte(
            Alerte(
                latitude = null,
                longitude = null,
                statut = "ECHEC",
                contactsNotifies = raison
            )
        )
        alerteEnCours = false
    }

    private fun vibrer() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vm.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 300, 200, 300), -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 300, 200, 300), -1)
        }
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
