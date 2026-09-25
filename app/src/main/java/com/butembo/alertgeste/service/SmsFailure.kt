package com.butembo.alertgeste.service

import android.telephony.SmsManager

/** Diagnostics only: an Android failure code does not establish the SIM balance. */
object SmsFailure {
    fun fromResult(code: Int): String {
        val description = when (code) {
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> "Échec SMS ; Android ne précise pas la cause"
            SmsManager.RESULT_ERROR_RADIO_OFF -> "Radio mobile désactivée"
            SmsManager.RESULT_ERROR_NO_SERVICE -> "Réseau mobile indisponible"
            SmsManager.RESULT_ERROR_NULL_PDU -> "Impossible de préparer les données SMS"
            SmsManager.RESULT_ERROR_LIMIT_EXCEEDED -> "Limite d’envoi SMS atteinte"
            SmsManager.RESULT_ERROR_FDN_CHECK_FAILURE -> "Numéro refusé par les restrictions de la SIM"
            SmsManager.RESULT_ERROR_SHORT_CODE_NOT_ALLOWED,
            SmsManager.RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED -> "Envoi vers ce numéro court non autorisé"
            SmsManager.RESULT_NETWORK_REJECT -> "Envoi refusé par le réseau"
            SmsManager.RESULT_NO_DEFAULT_SMS_APP -> "Configuration SMS ou SIM par défaut indisponible"
            else -> "Échec SMS signalé par Android"
        }
        return "$description (code Android $code)."
    }

    fun fromException(error: Exception): String {
        val description = when (error) {
            is SecurityException -> "Envoi refusé par Android"
            is IllegalArgumentException -> "Paramètres SMS refusés"
            is UnsupportedOperationException -> "Envoi SMS non pris en charge sur cet appareil"
            else -> "Impossible de soumettre le SMS à Android"
        }
        val reason = error.message?.replace('\n', ' ')?.replace('\r', ' ')?.take(160)
        return "$description (${error.javaClass.simpleName})" +
            if (reason.isNullOrBlank()) "." else " : $reason"
    }
}
