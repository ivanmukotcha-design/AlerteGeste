package com.butembo.alertgeste.domain

import org.junit.Assert.*
import org.junit.Test

class SmsTextTest {
    private fun denied(message: String) =
        SmsText.prepare(message) { throw SecurityException("getGroupIdLevel1") }

    @Test fun supportedPlatformDivisionIsKept() {
        var calls = 0
        val result = SmsText.prepare("message") {
            calls++
            listOf("mess", "age")
        }
        assertEquals(listOf("mess", "age"), result)
        assertEquals(1, calls)
    }

    @Test fun simPermissionFailureKeepsTheEntireEmergencyMessage() {
        val message = "Alerte d’urgence ! J’ai besoin d’aide immédiatement.\n".repeat(5) +
            "Position : https://maps.google.com/?q=-0.12345,29.12345\n— Élodie (+243812345678)"
        val parts = denied(message)
        assertTrue(parts.size > 1)
        assertEquals(message, parts.joinToString(""))
        assertTrue(parts.all { it.isNotEmpty() && it.length <= 66 })
    }

    @Test fun asciiSingleAndMultipartBoundariesAreRespected() {
        assertEquals(listOf("A".repeat(160)), denied("A".repeat(160)))
        assertEquals(listOf("A".repeat(153), "A".repeat(8)), denied("A".repeat(161)))
    }

    @Test fun extensionCharactersConsumeTwoSeptets() {
        assertEquals(1, denied("^".repeat(80)).size)
        val parts = denied("^".repeat(81))
        assertEquals(listOf("^".repeat(76), "^".repeat(5)), parts)
        val mixed = ("[]{}\\~|^\u000c").repeat(20)
        val mixedParts = denied(mixed)
        assertEquals(mixed, mixedParts.joinToString(""))
        assertTrue(mixedParts.all { it.length * 2 <= 153 })
    }

    @Test fun unicodeSingleAndMultipartBoundariesAreRespected() {
        assertEquals(listOf("漢".repeat(70)), denied("漢".repeat(70)))
        assertEquals(listOf("漢".repeat(66), "漢".repeat(5)), denied("漢".repeat(71)))
    }

    @Test fun oneUnicodeCharacterDeterminesLimitsForEveryPart() {
        val message = "A".repeat(300) + "—"
        val parts = denied(message)
        assertEquals(message, parts.joinToString(""))
        assertTrue(parts.all { it.length <= 66 })
    }

    @Test fun surrogatePairsAreNeverSplit() {
        val message = "é".repeat(65) + "🆘".repeat(50)
        val parts = denied(message)
        assertEquals(message, parts.joinToString(""))
        assertTrue(parts.all { it.length <= 66 })
        assertTrue(parts.none { Character.isHighSurrogate(it.last()) || Character.isLowSurrogate(it.first()) })
    }

    @Test fun accentsCurrencyWhitespaceAndCombinedEmojiArePreserved() {
        val message = ("  Élodie : 10 € — SOS 👨‍👩‍👧‍👦 e\u0301\n\t").repeat(25)
        val parts = denied(message)
        assertEquals(message, parts.joinToString(""))
        assertTrue(parts.all { it.isNotEmpty() && it.length <= 66 })
    }

    @Test fun backtickIsNotMistakenForGsmAscii() {
        val parts = denied("\u0060".repeat(71))
        assertEquals(listOf("\u0060".repeat(66), "\u0060".repeat(5)), parts)
    }

    @Test(expected = IllegalStateException::class)
    fun unrelatedPlatformFailuresAreNotSilentlyRetried() {
        SmsText.prepare("SOS") { error("Unrelated platform failure") }
    }

    @Test(expected = IllegalArgumentException::class)
    fun emptyMessageIsRejectedBeforeCallingAndroid() {
        SmsText.prepare("") { fail("Divider must not be called"); emptyList() }
    }
}
