package com.butembo.alertgeste.service

import android.telephony.SmsManager
import org.junit.Assert.*
import org.junit.Test

class SmsFailureTest {
    @Test fun securityExceptionPreservesTheBlockedOperation() {
        val reason = SmsFailure.fromException(SecurityException("getGroupIdLevel1"))
        assertTrue(reason.contains("SecurityException"))
        assertTrue(reason.contains("getGroupIdLevel1"))
    }

    @Test fun resultCodesAreKeptWithoutInventingABalanceProblem() {
        assertTrue(SmsFailure.fromResult(SmsManager.RESULT_ERROR_NO_SERVICE).contains("Réseau mobile indisponible"))
        val generic = SmsFailure.fromResult(SmsManager.RESULT_ERROR_GENERIC_FAILURE)
        assertTrue(generic.contains("ne précise pas la cause"))
        assertFalse(generic.contains("crédit"))
        assertTrue(SmsFailure.fromResult(9876).contains("9876"))
    }

    @Test fun exceptionDetailsAreBoundedAndSingleLine() {
        val text = SmsFailure.fromException(IllegalArgumentException("x\n".repeat(400)))
        assertFalse(text.contains('\n'))
        assertTrue(text.length <= 240)
        assertTrue(SmsFailure.fromException(UnsupportedOperationException()).contains("non pris en charge"))
    }
}
