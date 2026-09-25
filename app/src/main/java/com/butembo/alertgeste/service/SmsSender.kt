package com.butembo.alertgeste.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.SmsPart
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.domain.AlertStatus
import com.butembo.alertgeste.domain.SmsText
import com.butembo.alertgeste.receiver.SmsResultReceiver
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.CancellationException
import java.util.UUID

class SmsSender(private val context: Context, private val repository: AlertGesteRepository) {
    @Suppress("DEPRECATION")
    private fun manager(): SmsManager {
        val subscription = SubscriptionManager.getDefaultSmsSubscriptionId()
        check(subscription >= 0 && subscription != SubscriptionManager.DEFAULT_SUBSCRIPTION_ID) {
            "Choisissez une SIM par défaut pour les SMS dans les paramètres du téléphone."
        }
        return if (Build.VERSION.SDK_INT >= 31) context.getSystemService(SmsManager::class.java).createForSubscriptionId(subscription)
        else SmsManager.getSmsManagerForSubscriptionId(subscription)
    }

    @android.annotation.SuppressLint("MissingPermission")
    suspend fun send(alertId: Long, contacts: List<Contact>, message: String) {
        val sms = manager()
        val textParts = SmsText.prepare(message, sms::divideMessage)
        val records = contacts.associateWith { contact ->
            textParts.indices.map { index ->
                SmsPart(UUID.randomUUID().toString(), alertId, contact.id, contact.nom, contact.telephone, index)
            }
        }
        repository.prepareParts(alertId, records.values.flatten())
        for ((contact, parts) in records) {
            currentCoroutineContext().ensureActive()
            val callbacks = ArrayList(parts.map { part ->
                PendingIntent.getBroadcast(context, 0,
                    Intent(context, SmsResultReceiver::class.java)
                        .setAction("com.butembo.alertgeste.SMS_SENT")
                        .setData(Uri.parse("alertgeste://sms/${part.id}"))
                        .putExtra("part_id", part.id),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            })
            try {
                sms.sendMultipartTextMessage(contact.telephone, null, textParts, callbacks, null)
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (error: Exception) {
                val reason = SmsFailure.fromException(error)
                parts.forEach { repository.recordPart(it.id, AlertStatus.FAILED, reason) }
                callbacks.forEach { it.cancel() }
            }
        }
    }
}
