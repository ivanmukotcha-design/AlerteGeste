package com.butembo.alertgeste.receiver

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.domain.AlertStatus
import com.butembo.alertgeste.service.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: AlertGesteRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return
        val pending = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                withTimeout(8_000) {
                    repository.recoverInterrupted()
                    if (repository.getUtilisateurOnce()?.surveillanceActive != true) return@withTimeout
                    if (Build.VERSION.SDK_INT >= 29 &&
                        !DeviceReadiness.granted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        repository.setSurveillance(false)
                        AlertNotifications(context).information("Après redémarrage, ouvrez AlertGeste pour réactiver la surveillance.")
                        return@withTimeout
                    }
                    SurveillanceService.demarrer(context).onFailure {
                        repository.setSurveillance(false)
                        AlertNotifications(context).information("Ouvrez AlertGeste pour réactiver la surveillance.")
                    }
                }
            } catch (_: Exception) {
                AlertNotifications(context).information("La surveillance n’a pas repris. Ouvrez AlertGeste.")
            } finally { pending.finish() }
        }
    }
}

class AlerteActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            SurveillanceService.ACTION_ANNULER_ALERTE -> SurveillanceService.annuler(context)
            SurveillanceService.ACTION_ARRETER -> SurveillanceService.arreter(context)
        }
    }
}

@AndroidEntryPoint
class SmsResultReceiver : BroadcastReceiver() {
    @Inject lateinit var repository: AlertGesteRepository
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != "com.butembo.alertgeste.SMS_SENT") return
        val id = intent.getStringExtra("part_id") ?: return
        val code = resultCode
        val sent = code == Activity.RESULT_OK
        val reason = if (sent) "" else SmsFailure.fromResult(code)
        val pending = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                withTimeout(8_000) {
                    repository.recordPart(id, if (sent) AlertStatus.SENT else AlertStatus.FAILED, reason)?.let { alert ->
                        if (alert.statut != AlertStatus.SENDING) AlertNotifications(context).result(alert)
                    }
                }
            } catch (_: Exception) {
                // Keep the outcome unknown; never retry automatically (duplicate emergency SMS).
            } finally { pending.finish() }
        }
    }
}
