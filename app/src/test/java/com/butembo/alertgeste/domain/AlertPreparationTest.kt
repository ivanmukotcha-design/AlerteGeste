package com.butembo.alertgeste.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlertPreparationTest {
    @Test fun cancellingCountdownPreventsLocationAndSubmission() = runTest {
        var located = false
        var sent = false
        val job = launch {
            AlertPreparation().run({}, {}, { located = true })
            sent = true
        }
        advanceTimeBy(2000)
        job.cancelAndJoin()
        advanceUntilIdle()
        assertFalse(located)
        assertFalse(sent)
    }

    @Test fun cancellingDuringLocationPreventsSubmission() = runTest {
        var located = false
        var sent = false
        val job = launch {
            AlertPreparation().run({}, {}, { located = true; delay(8000) })
            sent = true
        }
        advanceTimeBy(6000)
        assertTrue(located)
        job.cancelAndJoin()
        advanceUntilIdle()
        assertFalse(sent)
    }

    @Test fun missingLocationStillAllowsSubmissionAfterCountdown() = runTest {
        val seconds = mutableListOf<Int>()
        var sent = false
        launch {
            val location: String? = AlertPreparation().run({ seconds.add(it) }, {}, { null })
            assertNull(location)
            sent = true
        }
        advanceTimeBy(4999)
        assertFalse(sent)
        advanceUntilIdle()
        assertEquals(listOf(5, 4, 3, 2, 1), seconds)
        assertTrue(sent)
    }

    @Test fun feedbackFollowsCountdownAndStopsBeforeLocation() = runTest {
        val pulses = mutableListOf<Pair<Int, Long>>()
        val stops = mutableListOf<Long>()
        val feedback = object : CountdownFeedback {
            override fun tick(seconds: Int) { pulses.add(seconds to currentTime) }
            override fun stop() { stops.add(currentTime) }
        }
        var sentAt: Long? = null
        launch {
            AlertPreparation(feedback).run({}, {
                assertEquals(listOf(5000L), stops)
            }, { null })
            sentAt = currentTime
        }
        advanceUntilIdle()
        assertEquals(listOf(5 to 0L, 4 to 1000L, 3 to 2000L, 2 to 3000L, 1 to 4000L), pulses)
        assertEquals(5000L, sentAt)
        assertEquals(1, stops.size)
    }

    @Test fun cancelledCountdownStopsFeedbackAndNeverProducesLaterPulses() = runTest {
        val pulses = mutableListOf<Int>()
        var stopped = false
        var sent = false
        val feedback = object : CountdownFeedback {
            override fun tick(seconds: Int) { pulses.add(seconds) }
            override fun stop() { stopped = true }
        }
        val job = launch {
            AlertPreparation(feedback).run({}, {}, { fail("Location must not start") })
            sent = true
        }
        advanceTimeBy(2400)
        job.cancelAndJoin()
        assertTrue(stopped)
        advanceUntilIdle()
        assertEquals(listOf(5, 4, 3), pulses)
        assertFalse(sent)
    }

    @Test fun failedCountdownAlsoStopsFeedback() = runTest {
        var stopped = false
        val feedback = object : CountdownFeedback {
            override fun tick(seconds: Int) = Unit
            override fun stop() { stopped = true }
        }
        try {
            AlertPreparation(feedback).run<Unit>({ error("Notification failure") }, {}, {
                fail("Location must not start")
            })
            fail("Expected countdown failure")
        } catch (_: IllegalStateException) {
            assertTrue(stopped)
        }
    }
}
