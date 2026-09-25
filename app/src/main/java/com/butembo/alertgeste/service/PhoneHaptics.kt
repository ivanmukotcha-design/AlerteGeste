package com.butembo.alertgeste.service

import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import com.butembo.alertgeste.domain.CountdownFeedback

/** Finite effects only; no thread, timer, repeating waveform or extra wake lock. */
class PhoneHaptics(context: Context) : CountdownFeedback {
    @Suppress("DEPRECATION")
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= 31) {
        context.getSystemService(VibratorManager::class.java)?.defaultVibrator
    } else {
        context.getSystemService(Vibrator::class.java)
    }

    fun gestureSaved() = play(longArrayOf(0, 80, 100, 140), attention = false)

    override fun tick(seconds: Int) {
        // The first double pulse also confirms that the gesture/SOS was accepted.
        val pattern = when (seconds) {
            5 -> longArrayOf(0, 250, 150, 250)
            1 -> longArrayOf(0, 300)
            in 2..4 -> longArrayOf(0, 180)
            else -> return
        }
        play(pattern, attention = true)
    }

    @Suppress("DEPRECATION")
    private fun play(timings: LongArray, attention: Boolean) {
        try {
            val device = vibrator ?: return
            if (!device.hasVibrator()) return
            val effect = VibrationEffect.createWaveform(timings, -1)
            if (Build.VERSION.SDK_INT >= 33) {
                device.vibrate(effect, VibrationAttributes.Builder().setUsage(
                    if (attention) VibrationAttributes.USAGE_NOTIFICATION else VibrationAttributes.USAGE_TOUCH
                ).build())
            } else {
                device.vibrate(effect, AudioAttributes.Builder()
                    .setUsage(if (attention) AudioAttributes.USAGE_NOTIFICATION else AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build())
            }
        } catch (_: RuntimeException) {
            // An unavailable/disabled vibrator must never interrupt an alert or a save.
        }
    }

    override fun stop() {
        try { vibrator?.cancel() } catch (_: RuntimeException) { /* Best effort on vendor devices. */ }
    }
}
