package com.butembo.alertgeste

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlertGesteApp : Application() {

    companion object {
        const val CHANNEL_SURVEILLANCE = "channel_surveillance"
        const val CHANNEL_ALERTE = "channel_alerte_silent_v2"
    }

    override fun onCreate() {
        super.onCreate()
        creerCanauxNotification()
    }

    private fun creerCanauxNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)

            // Canal service surveillance (discret)
            val canalSurveillance = NotificationChannel(
                CHANNEL_SURVEILLANCE,
                "Surveillance AlertGeste",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Service de surveillance en arrière-plan"
                setShowBadge(false)
            }

            // Canal alerte (urgent)
            val canalAlerte = NotificationChannel(
                CHANNEL_ALERTE,
                "Alertes d'urgence",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications d'alerte d'urgence"
                setSound(null, null)
                enableVibration(false)
                lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
            }

            manager.createNotificationChannel(canalSurveillance)
            manager.createNotificationChannel(canalAlerte)
        }
    }
}
