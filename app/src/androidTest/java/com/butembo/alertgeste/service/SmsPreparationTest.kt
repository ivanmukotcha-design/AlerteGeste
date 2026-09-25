package com.butembo.alertgeste.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.butembo.alertgeste.domain.SmsText
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/** Preparation only: this test never calls an SMS sending API or reads app contacts. */
@RunWith(AndroidJUnit4::class)
class SmsPreparationTest {
    @Suppress("DEPRECATION")
    @Test fun preparesLongUnicodeAlertWithRealSmsManagerWithoutPhoneStatePermission() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        assertEquals(PackageManager.PERMISSION_DENIED,
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE))
        val subscription = SubscriptionManager.getDefaultSmsSubscriptionId()
        val sms = if (Build.VERSION.SDK_INT >= 31) {
            context.getSystemService(SmsManager::class.java).createForSubscriptionId(subscription)
        } else SmsManager.getSmsManagerForSubscriptionId(subscription)
        val text = "Alerte d’urgence — à vérifier 🆘\n".repeat(10)
        var denied = false
        val parts = SmsText.prepare(text) {
            try { sms.divideMessage(it) }
            catch (e: SecurityException) { denied = true; throw e }
        }
        assertTrue(parts.size > 1)
        assertEquals(text, parts.joinToString(""))
        assertTrue(parts.none { it.isEmpty() || Character.isHighSurrogate(it.last()) || Character.isLowSurrogate(it.first()) })
        if (denied) assertTrue(parts.all { it.length <= 66 })
        Log.i("SmsPreparationTest", "SIM access denied=" + denied + "; prepared parts=" + parts.size + "; no SMS sent")
    }
}
