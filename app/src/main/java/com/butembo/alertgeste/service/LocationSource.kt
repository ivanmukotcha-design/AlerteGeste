package com.butembo.alertgeste.service

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

/** Platform-only location: no Play Services requirement, no continuous GPS tracking. */
class LocationSource(private val context: Context) {
    private val manager = context.getSystemService(LocationManager::class.java)

    private fun usable(location: Location): Boolean {
        val age = (SystemClock.elapsedRealtimeNanos() - location.elapsedRealtimeNanos) / 1_000_000
        return age in 0..60_000 && location.hasAccuracy() && location.accuracy <= 500
    }

    @SuppressLint("MissingPermission")
    suspend fun current(): Location? {
        if (!DeviceReadiness.hasLocation(context)) return null
        val providers = listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER).filter { provider ->
            (provider != LocationManager.GPS_PROVIDER || DeviceReadiness.granted(context, Manifest.permission.ACCESS_FINE_LOCATION)) &&
                runCatching { manager.isProviderEnabled(provider) }.getOrDefault(false)
        }
        if (providers.isEmpty()) return null
        val cached = providers.mapNotNull { runCatching { manager.getLastKnownLocation(it) }.getOrNull() }
            .filter(::usable).minByOrNull { it.accuracy }
        if (cached != null) return cached
        return withTimeoutOrNull(8_000) {
            suspendCancellableCoroutine { continuation ->
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        if (usable(location) && continuation.isActive) {
                            manager.removeUpdates(this)
                            continuation.resume(location)
                        }
                    }
                    override fun onProviderEnabled(provider: String) = Unit
                    override fun onProviderDisabled(provider: String) = Unit
                    @Deprecated("Required on older Android versions")
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
                }
                continuation.invokeOnCancellation { manager.removeUpdates(listener) }
                var registered = false
                for (provider in providers) {
                    try {
                        manager.requestLocationUpdates(provider, 1000L, 0f, listener, Looper.getMainLooper())
                        registered = true
                    } catch (_: Exception) { /* Try another available provider. */ }
                }
                if (!registered && continuation.isActive) continuation.resume(null)
                if (!continuation.isActive) manager.removeUpdates(listener)
            }
        }
    }
}
