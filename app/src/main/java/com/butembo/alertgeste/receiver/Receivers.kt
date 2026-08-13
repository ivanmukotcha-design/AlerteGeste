package com.butembo.alertgeste.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.service.SurveillanceService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    
    @Inject lateinit var repository: AlertGesteRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || 
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val utilisateur = repository.getUtilisateurOnce()
                if (utilisateur?.surveillanceActive == true) {
                    SurveillanceService.demarrer(context)
                }
            }
        }
    }
}

class AlerteActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SurveillanceService.ACTION_ANNULER_ALERTE) {
            val serviceIntent = Intent(context, SurveillanceService::class.java).apply {
                action = SurveillanceService.ACTION_ANNULER_ALERTE
            }
            context.startService(serviceIntent)
        }
    }
}
