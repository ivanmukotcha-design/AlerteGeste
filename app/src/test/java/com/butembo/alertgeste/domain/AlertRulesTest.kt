package com.butembo.alertgeste.domain

import org.junit.Assert.*
import org.junit.Test

class AlertRulesTest {
    @Test fun failedSummaryNeverClaimsAllPartsWereSent() {
        val text = AlertStatus.summary(AlertStatus.FAILED, 0, 1)
        assertTrue(text.contains("Aucun envoi complet confirmé (0/1"))
        assertTrue(text.contains("Échec signalé"))
        assertFalse(text.contains("toutes les parties"))
        assertFalse(text.contains("opérateur"))
    }

    @Test fun partialSummaryExplainsZeroCompleteRecipientsDespiteSomeSentParts() {
        val text = AlertStatus.summary(AlertStatus.PARTIAL, 0, 1)
        assertTrue(text.contains("0/1"))
        assertTrue(text.contains("Certaines parties ont été envoyées"))
    }

    @Test fun pendingAndUnknownSummariesDoNotAssertFailureOrDelivery() {
        assertTrue(AlertStatus.summary(AlertStatus.SENDING, 0, 2).contains("encore en cours"))
        val text = AlertStatus.summary(AlertStatus.UNKNOWN, 1, 2)
        assertTrue(text.contains("1/2"))
        assertTrue(text.contains("résultat reste inconnu"))
        assertFalse(text.contains("Échec signalé"))
    }

    @Test fun successfulSummaryStillDoesNotClaimDelivery() {
        val text = AlertStatus.summary(AlertStatus.SENT, 2, 2)
        assertTrue(text.contains("2/2"))
        assertTrue(text.contains("confirmé par Android"))
        assertTrue(text.contains("Réception non vérifiée"))
    }

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
