package com.butembo.alertgeste.domain

import org.junit.Assert.*
import org.junit.Test

class AlertRulesTest {
    @Test fun allPartsMustBeConfirmedBeforeSuccess() {
        assertEquals(AlertStatus.SENDING, AlertStatus.aggregate(listOf(AlertStatus.SENT, AlertStatus.PENDING)))
        assertEquals(AlertStatus.SENT, AlertStatus.aggregate(listOf(AlertStatus.SENT, AlertStatus.SENT)))
    }
    @Test fun failuresAndMissingCallbacksNeverBecomeSuccess() {
        assertEquals(AlertStatus.FAILED, AlertStatus.aggregate(listOf(AlertStatus.FAILED, AlertStatus.FAILED)))
        assertEquals(AlertStatus.PARTIAL, AlertStatus.aggregate(listOf(AlertStatus.SENT, AlertStatus.FAILED)))
        assertEquals(AlertStatus.UNKNOWN, AlertStatus.aggregate(listOf(AlertStatus.SENT, AlertStatus.UNKNOWN)))
        assertEquals(AlertStatus.FAILED, AlertStatus.aggregate(emptyList()))
    }
    @Test fun normalizedPhonesPreserveInternationalPrefix() {
        assertEquals("+243812345678", InputRules.phone(" +243 (81) 234-5678 "))
        assertNull(InputRules.phone("     "))
        assertNull(InputRules.phone("+243ABC123456"))
        assertNull(InputRules.phone("123"))
        assertNull(InputRules.phone("++243812345678"))
        assertNull(InputRules.phone("1234567890123456"))
    }
    @Test fun emergencyMessageAndIdentityAreRequiredAndBounded() {
        assertTrue(InputRules.validProfile("Alice", "+243812345678", "Aidez-moi"))
        assertFalse(InputRules.validProfile(" ", "+243812345678", "Aidez-moi"))
        assertFalse(InputRules.validProfile("Alice", "+243812345678", " "))
        assertFalse(InputRules.validProfile("Alice", "+243812345678", "a".repeat(481)))
    }
}
