package com.butembo.alertgeste.service

import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.butembo.alertgeste.AlertGesteApp
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.domain.AlertStatus
import com.butembo.alertgeste.receiver.AlerteActionReceiver
import com.butembo.alertgeste.ui.MainActivity

class AlertNotifications(private val context: Context) {
    companion object { const val SERVICE_ID = 1; const val ALERT_ID = 2; const val RESULT_ID = 3 }
    private val manager = context.getSystemService(NotificationManager::class.java)
    private fun openApp() = PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    private fun action(action: String) = PendingIntent.getBroadcast(context, action.hashCode(),
        Intent(context, AlerteActionReceiver::class.java).setAction(action),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    fun service(text: String): Notification = NotificationCompat.Builder(context, AlertGesteApp.CHANNEL_SURVEILLANCE)
        .setSmallIcon(R.drawable.ic_shield).setContentTitle("AlertGeste")
        .setContentText(text).setContentIntent(openApp()).setOngoing(true).setSilent(true)
        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        .addAction(R.drawable.ic_cancel, "Arrêter la surveillance", action(SurveillanceService.ACTION_ARRETER))
        .build()

    fun progress(text: String, cancellable: Boolean) {
        val builder = NotificationCompat.Builder(context, AlertGesteApp.CHANNEL_ALERTE)
            .setSmallIcon(R.drawable.ic_warning).setContentTitle("Alerte en préparation")
            .setContentText(text).setContentIntent(openApp()).setOngoing(true)
            .setSilent(true).setOnlyAlertOnce(true).setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        if (cancellable) builder.addAction(R.drawable.ic_cancel, "ANNULER", action(SurveillanceService.ACTION_ANNULER_ALERTE))
        post(ALERT_ID, builder.build())
    }

    fun result(alert: Alerte) {
        // A late callback for an older alert must not remove a new alert's cancel action.
        post(RESULT_ID, NotificationCompat.Builder(context, AlertGesteApp.CHANNEL_ALERTE)
            .setSmallIcon(if (alert.statut == AlertStatus.SENT) R.drawable.ic_check else R.drawable.ic_warning)
            .setContentTitle(AlertStatus.label(alert.statut)).setContentText(alert.detail)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.detail))
            .setContentIntent(openApp()).setAutoCancel(true).setSilent(true).setOnlyAlertOnce(true)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE).build())
    }

    fun information(text: String) {
        post(RESULT_ID, NotificationCompat.Builder(context, AlertGesteApp.CHANNEL_ALERTE)
            .setSmallIcon(R.drawable.ic_warning).setContentTitle("AlertGeste : action nécessaire")
            .setContentText(text).setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentIntent(openApp()).setAutoCancel(true).setSilent(true).build())
    }

    fun clearProgress() = manager.cancel(ALERT_ID)
    fun clearAll() { manager.cancel(ALERT_ID); manager.cancel(RESULT_ID) }
    fun updateService(text: String) = post(SERVICE_ID, service(text))
    @android.annotation.SuppressLint("MissingPermission")
    private fun post(id: Int, notification: Notification) {
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) manager.notify(id, notification)
    }
}
