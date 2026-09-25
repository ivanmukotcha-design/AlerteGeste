package com.butembo.alertgeste.service

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow

object DeviceReadiness {
    val powerChanges = MutableStateFlow(0)
    fun granted(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    fun permissions(): Array<String> = buildList {
        add(Manifest.permission.SEND_SMS)
        add(Manifest.permission.ACCESS_COARSE_LOCATION)
        add(Manifest.permission.ACCESS_FINE_LOCATION)
        if (Build.VERSION.SDK_INT >= 33) add(Manifest.permission.POST_NOTIFICATIONS)
    }.toTypedArray()

    fun hasLocation(context: Context) = granted(context, Manifest.permission.ACCESS_COARSE_LOCATION) ||
        granted(context, Manifest.permission.ACCESS_FINE_LOCATION)

    fun problem(context: Context, requireLocation: Boolean = true): String? = when {
        !context.packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_MESSAGING) ->
            "Cet appareil ne peut pas envoyer de SMS."
        !granted(context, Manifest.permission.SEND_SMS) -> "Autorisez les SMS pour envoyer une alerte."
        requireLocation && !hasLocation(context) -> "Autorisez la localisation pour démarrer la surveillance."
        !NotificationManagerCompat.from(context).areNotificationsEnabled() ->
            "Activez les notifications pour pouvoir annuler une alerte."
        context.getSystemService(NotificationManager::class.java)
            .getNotificationChannel(com.butembo.alertgeste.AlertGesteApp.CHANNEL_ALERTE)?.importance == NotificationManager.IMPORTANCE_NONE ->
            "Activez le canal de notifications des alertes pour pouvoir annuler."
        requireLocation && !locationEnabled(context) -> "Activez la localisation du téléphone avant de démarrer la surveillance."
        else -> null
    }

    fun locationEnabled(context: Context): Boolean {
        val manager = context.getSystemService(LocationManager::class.java)
        return if (Build.VERSION.SDK_INT >= 28) manager.isLocationEnabled
        else manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun wakeUpSensor(context: Context): Sensor? =
        context.getSystemService(SensorManager::class.java).getDefaultSensor(Sensor.TYPE_ACCELEROMETER, true)

    fun allowWakeLock(context: Context) = context.getSharedPreferences("power", Context.MODE_PRIVATE)
        .getBoolean("reliable_without_wakeup", false)

    fun setAllowWakeLock(context: Context, enabled: Boolean) {
        context.getSharedPreferences("power", Context.MODE_PRIVATE).edit()
            .putBoolean("reliable_without_wakeup", enabled).apply()
        powerChanges.value += 1
    }
}
