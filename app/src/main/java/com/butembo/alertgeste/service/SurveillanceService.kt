package com.butembo.alertgeste.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.*
import android.os.*
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.domain.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@AndroidEntryPoint
class SurveillanceService : LifecycleService(), SensorEventListener {
    @Inject lateinit var repository: AlertGesteRepository
    private lateinit var sensors: SensorManager
    private lateinit var notifications: AlertNotifications
    private lateinit var haptics: PhoneHaptics
    private var sensor: Sensor? = null
    private var listening = false
    private var monitoringRequested = false
    private var profile: GesteProfil? = null
    private var watcher: Job? = null
    private var alertJob: Job? = null
    private lateinit var recovery: Deferred<Unit>
    private var alertId: Long? = null
    private var phase: String? = null
    private var destroyed = false
    private var cooldownUntil = 0L
    private var detector = ShakeDetector()
    private val filter = MotionFilter()
    private var sensorWakeLock: PowerManager.WakeLock? = null
    private var alertWakeLock: PowerManager.WakeLock? = null
    private val commands = Mutex()

    companion object {
        const val ACTION_DEMARRER = "ACTION_DEMARRER"
        const val ACTION_ARRETER = "ACTION_ARRETER"
        const val ACTION_SOS_MANUEL = "ACTION_SOS_MANUEL"
        const val ACTION_ANNULER_ALERTE = "ACTION_ANNULER_ALERTE"
        fun demarrer(context: Context): Result<Unit> = start(context, ACTION_DEMARRER)
        fun declencherSos(context: Context): Result<Unit> = start(context, ACTION_SOS_MANUEL)
        private fun start(context: Context, action: String): Result<Unit> = runCatching {
            DeviceReadiness.problem(context, requireLocation = action != ACTION_SOS_MANUEL)?.let { error(it) }
            ContextCompat.startForegroundService(context, Intent(context, SurveillanceService::class.java).setAction(action))
            Unit
        }
        fun arreter(context: Context) {
            if (SurveillanceState.state.value.running)
                context.startService(Intent(context, SurveillanceService::class.java).setAction(ACTION_ARRETER))
        }
        fun annuler(context: Context) {
            if (SurveillanceState.state.value.running)
                context.startService(Intent(context, SurveillanceService::class.java).setAction(ACTION_ANNULER_ALERTE))
        }
    }

    override fun onCreate() {
        super.onCreate()
        sensors = getSystemService(SensorManager::class.java)
        sensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER, true)
            ?: sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        notifications = AlertNotifications(this)
        haptics = PhoneHaptics(this)
        recovery = lifecycleScope.async { repository.recoverInterrupted() }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_ARRETER -> {
                monitoringRequested = false
                stopSensor()
                cancelAlert()
                lifecycleScope.launch {
                    commands.withLock {
                        try { repository.setSurveillance(false) }
                        catch (_: Exception) { notifications.information("Impossible de mémoriser l’arrêt. Vérifiez les paramètres avant de redémarrer le téléphone.") }
                        monitoringRequested = false
                        stopSensor()
                        // A queued SOS may have begun while this stop command waited for the lock.
                        cancelAlert()
                        SurveillanceState.update { it.copy(monitoring = false) }
                        if (alertJob == null) stopSelf(startId)
                    }
                }
                return Service.START_NOT_STICKY
            }
            ACTION_ANNULER_ALERTE -> {
                cancelAlert()
                if (!monitoringRequested && alertJob == null) stopSelf()
                return if (monitoringRequested) Service.START_STICKY else Service.START_NOT_STICKY
            }
        }
        val manualOnly = (
            intent?.action == ACTION_SOS_MANUEL &&
                (!monitoringRequested || !DeviceReadiness.hasLocation(this) || !DeviceReadiness.locationEnabled(this))
            )
        try {
            DeviceReadiness.problem(this, requireLocation = intent?.action != ACTION_SOS_MANUEL)?.let { error(it) }
            val foregroundType = when {
                Build.VERSION.SDK_INT >= 29 && DeviceReadiness.hasLocation(this) && DeviceReadiness.locationEnabled(this) -> ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
                Build.VERSION.SDK_INT >= 34 -> ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
                else -> 0
            }
            ServiceCompat.startForeground(this, AlertNotifications.SERVICE_ID,
                notifications.service("Préparation de la protection…"), foregroundType)
            SurveillanceState.update { it.copy(running = true, message = "") }
        } catch (e: Exception) {
            fail(e.message ?: "Démarrage du service refusé.")
            return Service.START_NOT_STICKY
        }
        lifecycleScope.launch {
            commands.withLock {
                try {
                    recovery.await()
                    if (manualOnly) repository.setSurveillance(false)
                    if (intent?.action == ACTION_DEMARRER) {
                        require(sensor != null) { "Accéléromètre indisponible." }
                        require(repository.getProfilActif().first()?.estEnregistre == true) { "Calibrez votre geste avant d’activer la surveillance." }
                        require(repository.getTousContactsOnce().isNotEmpty()) { "Ajoutez un contact de confiance." }
                        repository.setSurveillance(true)
                    }
                    monitoringRequested = repository.getUtilisateurOnce()?.surveillanceActive == true
                    if (watcher == null) watchConfiguration()
                    if (intent?.action == ACTION_SOS_MANUEL) {
                        triggerAlert()
                    } else if (!monitoringRequested) {
                        stopSelf(startId)
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    fail(e.message ?: "Impossible de préparer la surveillance.")
                }
            }
        }
        return if (manualOnly) Service.START_NOT_STICKY else Service.START_STICKY
    }

    private fun watchConfiguration() {
        watcher = lifecycleScope.launch {
            try {
                combine(repository.getUtilisateur(), repository.getProfilActif(), repository.getTousContacts(), SurveillanceState.training, DeviceReadiness.powerChanges) { user, p, contacts, training, _ ->
                    Configuration(user?.surveillanceActive == true, p, contacts.isNotEmpty(), training)
                }.collect { config ->
                    val previousProfile = profile
                    profile = config.profile
                    monitoringRequested = config.active && profile?.estEnregistre == true && config.hasContacts
                    if (config.active && !monitoringRequested) {
                        repository.setSurveillance(false)
                        notifications.information("Surveillance arrêtée : vérifiez le geste et les contacts.")
                        if (alertJob == null) stopSelf()
                    }
                    if (previousProfile != profile) detector = ShakeDetector(
                        threshold = profile?.seuilMin ?: 5f,
                        windowMs = profile?.fenetreTempsMs ?: 2500L
                    )
                    SurveillanceState.update { it.copy(monitoring = monitoringRequested, training = config.training) }
                    refreshSensor()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                fail("Surveillance interrompue : impossible de lire la configuration.")
            }
        }
    }
    private data class Configuration(val active: Boolean, val profile: GesteProfil?, val hasContacts: Boolean, val training: Boolean)

    private fun refreshSensor() {
        if (destroyed) return
        if (!monitoringRequested || SurveillanceState.training.value || alertJob != null) {
            stopSensor()
        } else if (!listening) {
            val selected = sensor ?: return fail("Accéléromètre indisponible.")
            filter.reset(); detector.reset()
            // Batch on FIFO wake-up sensors to avoid waking the CPU for every sample.
            val latencyUs = if (selected.isWakeUpSensor && selected.fifoMaxEventCount > 0) 400_000 else 0
            listening = sensors.registerListener(this, selected, 40_000, latencyUs)
            if (!listening) return fail("Impossible d’activer le capteur.")
            if (!selected.isWakeUpSensor && DeviceReadiness.allowWakeLock(this)) acquireSensorLock()
        }
        if (listening && sensor?.isWakeUpSensor != true) {
            if (DeviceReadiness.allowWakeLock(this) && sensorWakeLock == null) acquireSensorLock()
            else if (!DeviceReadiness.allowWakeLock(this)) {
                sensorWakeLock?.let { if (it.isHeld) it.release() }
                sensorWakeLock = null
            }
        }
        val text = when {
            phase != null -> AlertStatus.label(phase!!)
            SurveillanceState.training.value && monitoringRequested -> "Surveillance suspendue pendant la calibration"
            monitoringRequested && sensor?.isWakeUpSensor != true && !DeviceReadiness.allowWakeLock(this) ->
                "Surveillance active — détection en veille non garantie"
            monitoringRequested -> "Surveillance active"
            else -> "Surveillance arrêtée"
        }
        notifications.updateService(text)
        SurveillanceState.update { it.copy(message = text) }
    }

    @android.annotation.SuppressLint("WakelockTimeout")
    private fun acquireSensorLock() {
        // Explicit opt-in only on devices lacking a wake-up accelerometer. Released on pause/stop.
        sensorWakeLock = getSystemService(PowerManager::class.java)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlertGeste:gesture").apply { acquire() }
    }
    private fun stopSensor() {
        if (listening) sensors.unregisterListener(this)
        listening = false
        sensorWakeLock?.let { if (it.isHeld) it.release() }
        sensorWakeLock = null
        filter.reset(); detector.reset()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (!listening || alertJob != null || SurveillanceState.training.value || SystemClock.elapsedRealtime() < cooldownUntil) return
        val magnitude = filter.magnitude(event.values[0], event.values[1], event.values[2], event.timestamp)
        if (detector.add(magnitude, event.timestamp / 1_000_000)) triggerAlert()
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    private fun setPhase(value: String?) {
        phase = value
        SurveillanceState.update { it.copy(alertPhase = value) }
    }
    private fun cancelAlert() {
        if (phase == AlertStatus.COUNTDOWN || phase == AlertStatus.LOCATING) {
            haptics.stop()
            alertJob?.cancel()
        }
        else if (phase == AlertStatus.SENDING) notifications.information("Envoi déjà engagé. Les SMS transmis ne peuvent plus être annulés.")
    }

    private fun triggerAlert() {
        if (alertJob != null || SurveillanceState.training.value) return
        stopSensor()
        setPhase(AlertStatus.COUNTDOWN)
        alertJob = lifecycleScope.launch {
            var submitted = false
            try {
                val user = repository.getUtilisateurOnce() ?: error("Configurez votre profil.")
                val contacts = repository.getTousContactsOnce()
                require(contacts.isNotEmpty()) { "Aucun contact de confiance." }
                DeviceReadiness.problem(this@SurveillanceService, requireLocation = false)?.let { error(it) }
                val id = repository.enregistrerAlerte(Alerte(latitude = null, longitude = null,
                    statut = AlertStatus.COUNTDOWN, contactsNotifies = ""))
                alertId = id
                alertWakeLock = getSystemService(PowerManager::class.java)
                    .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AlertGeste:alert").apply { acquire(60_000) }
                val location = AlertPreparation(haptics).run(
                    onCountdown = { seconds ->
                        val text = "Alerte dans $seconds seconde(s). Appuyez sur ANNULER en cas d’erreur."
                        notifications.progress(text, true)
                        SurveillanceState.update { it.copy(message = text) }
                    },
                    onLocating = {
                        setPhase(AlertStatus.LOCATING)
                        repository.setAlertStatus(id, AlertStatus.LOCATING)
                        notifications.progress("Recherche de position, au maximum 8 secondes. Annulation possible.", true)
                    },
                    locate = { LocationSource(this@SurveillanceService).current() }
                )
                ensureActive()
                repository.setLocation(id, location?.latitude, location?.longitude)
                val message = buildString {
                    append(user.messageAlerte)
                    if (location != null) append("\nPosition (précision ~${location.accuracy.toInt()} m) : https://maps.google.com/?q=${location.latitude},${location.longitude}")
                    else append("\nPosition indisponible.")
                    append("\n— ${user.nom} (${user.telephone})")
                }
                ensureActive()
                setPhase(AlertStatus.SENDING)
                notifications.progress("Transmission des SMS. Annulation désormais impossible.", false)
                submitted = true
                SmsSender(this@SurveillanceService, repository).send(id, contacts, message)
                val result = withTimeoutOrNull(30_000) {
                    repository.observeAlerte(id).filterNotNull().first { it.statut != AlertStatus.SENDING }
                } ?: repository.expireAlert(id)
                result?.let { notifications.result(it) }
            } catch (e: CancellationException) {
                withContext(NonCancellable) {
                    alertId?.let { id ->
                        if (submitted) repository.expireAlert(id)
                        else repository.setAlertStatus(id, if (destroyed) AlertStatus.INTERRUPTED else AlertStatus.CANCELLED)
                    }
                }
                throw e
            } catch (e: Exception) {
                val detail = e.message ?: "Impossible d’envoyer l’alerte."
                alertId?.let { id ->
                    val existing = repository.getAlerte(id)
                    if (existing?.statut == AlertStatus.SENDING) {
                        repository.expireAlert(id)
                    } else {
                        repository.setAlertStatus(id, AlertStatus.FAILED, detail)
                    }
                    repository.getAlerte(id)?.let { notifications.result(it) }
                } ?: notifications.information(detail)
                SurveillanceState.update { it.copy(message = detail) }
            } finally {
                haptics.stop()
                notifications.clearProgress()
                alertWakeLock?.let { if (it.isHeld) it.release() }; alertWakeLock = null
                cooldownUntil = SystemClock.elapsedRealtime() + 30_000
                alertId = null
                setPhase(null)
                alertJob = null
                refreshSensor()
                if (!monitoringRequested && !destroyed) stopSelf()
            }
        }
    }

    private fun fail(message: String) {
        SurveillanceState.update { it.copy(message = message, monitoring = false) }
        notifications.information(message)
        lifecycleScope.launch {
            try { repository.setSurveillance(false) } catch (_: Exception) { /* State remains visibly inactive. */ }
            finally { stopSelf() }
        }
    }

    override fun onTimeout(startId: Int) { stopSelf() }
    override fun onTimeout(startId: Int, fgsType: Int) { stopSelf() }

    override fun onDestroy() {
        destroyed = true
        haptics.stop()
        stopSensor()
        notifications.clearProgress()
        alertWakeLock?.let { if (it.isHeld) it.release() }
        SurveillanceState.update { it.copy(running = false, monitoring = false, alertPhase = null,
            message = if (monitoringRequested) "Surveillance interrompue. Réactivez-la." else "Surveillance arrêtée.") }
        super.onDestroy()
    }
}
