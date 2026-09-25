package com.butembo.alertgeste.domain

import org.junit.Assert.*
import org.junit.Test

class ShakeDetectorTest {
    @Test fun sustainedMovementIsOnlyOnePeak() {
        val detector = ShakeDetector()
        repeat(100) { assertFalse(detector.add(12f, it * 40L)) }
        assertTrue(detector.count <= 1)
    }
    @Test fun threeDistinctPeaksTriggerExactlyOnce() {
        val detector = ShakeDetector()
        val events = listOf(0L to 0f, 40L to 8f, 160L to 0f, 400L to 9f,
            520L to 0f, 800L to 20f, 840L to 20f, 880L to 20f, 920L to 0f)
        assertEquals(1, events.count { (time, value) -> detector.add(value, time) })
    }
    @Test fun separatedShakesOutsideWindowDoNotTrigger() {
        val detector = ShakeDetector()
        for (time in listOf(0L, 2000L, 4000L)) {
            detector.add(0f, time)
            assertFalse(detector.add(10f, time + 40))
        }
    }
    @Test fun shortVibrationBurstIsDebounced() {
        val detector = ShakeDetector()
        repeat(6) {
            detector.add(0f, it * 80L)
            assertFalse(detector.add(10f, it * 80L + 40))
        }
    }
    @Test fun continuousShakingDoesNotRequireReturnToRest() {
        val detector = ShakeDetector()
        assertFalse(detector.add(6f, 0))
        assertFalse(detector.add(4f, 200))
        assertFalse(detector.add(9f, 400))
        assertFalse(detector.add(4f, 600))
        assertFalse(detector.add(9f, 800))
        assertTrue(detector.add(4f, 1000))
        assertEquals(3, detector.count)
    }
    @Test fun strongerEmergencyShakesAreNotRejectedByAnUpperLimit() {
        val detector = ShakeDetector()
        detector.add(40f, 0); detector.add(0f, 100)
        detector.add(45f, 400); detector.add(0f, 500)
        assertFalse(detector.add(50f, 800))
        assertTrue(detector.add(0f, 900))
    }
    @Test fun resetDiscardsPreviousPeaks() {
        val detector = ShakeDetector()
        detector.add(8f, 0); detector.add(0f, 100); detector.add(8f, 400)
        detector.reset()
        assertFalse(detector.add(8f, 800))
    }
    @Test fun gravityDoesNotCountAsMotionOnStartupOrResume() {
        val filter = MotionFilter()
        assertEquals(0f, filter.magnitude(0f, 0f, 9.81f, 1_000_000), 0.001f)
        repeat(50) { assertEquals(0f, filter.magnitude(0f, 0f, 9.81f, (it + 1) * 40_000_000L), 0.01f) }
        assertEquals(0f, filter.magnitude(9.81f, 0f, 0f, 10_000_000_000L), 0.001f)
    }
    @Test fun invalidSamplesDoNotTrigger() {
        val detector = ShakeDetector()
        assertFalse(detector.add(Float.NaN, 0))
        assertFalse(detector.add(Float.POSITIVE_INFINITY, 400))
        assertFalse(detector.add(9f, -1))
        assertEquals(0, detector.count)
    }

    @Test fun highPlateauWithSmallNoiseDoesNotBecomeRepeatedShakes() {
        val detector = ShakeDetector()
        repeat(500) { assertFalse(detector.add(12f + (it % 3) * 0.2f, it * 40L)) }
    }

    @Test fun ordinaryLowAmplitudeMovementDoesNotTrigger() {
        val detector = ShakeDetector(threshold = 3f)
        repeat(500) { assertFalse(detector.add(2f * kotlin.math.abs(kotlin.math.sin(it * 0.3f)), it * 40L)) }
    }

    @Test fun comfortablePaceWorksAndLegacyWindowIsRespected() {
        val current = ShakeDetector(windowMs = 2500)
        val legacy = ShakeDetector(windowMs = 1500)
        var currentTriggers = 0
        var legacyTriggers = 0
        repeat(60) { i ->
            val magnitude = if (i % 25 in 1..3) 8f else 0f
            if (current.add(magnitude, i * 40L)) currentTriggers++
            if (legacy.add(magnitude, i * 40L)) legacyTriggers++
        }
        assertEquals(1, currentTriggers)
        assertEquals(0, legacyTriggers)
    }

    @Test fun calibrationLearnsGentleAndStrongMovementsAndCanReplayThem() {
        for (amplitude in listOf(3.5f, 8f, 35f)) {
            val calibration = ShakeCalibration()
            val trace = (0..45).map { it * 40L to if (it % 15 in 1..3) amplitude else 0f }
            assertEquals(1, trace.count { (time, value) -> calibration.add(value, time) })
            assertEquals((amplitude * 0.65f).coerceIn(3f, 12f), calibration.threshold, 0.01f)
            val monitoring = ShakeDetector(threshold = calibration.threshold)
            assertEquals(1, trace.count { (time, value) -> monitoring.add(value, time) })
        }
    }

    @Test fun realAccelerometerStyleWaveformWorksThroughGravityFilter() {
        val filter = MotionFilter()
        val detector = ShakeDetector(threshold = 3f)
        var triggers = 0
        repeat(80) { i ->
            val time = i * 40_000_000L
            val x = 8f * kotlin.math.sin(i * 0.04f * 2f * Math.PI.toFloat() * 2f)
            if (detector.add(filter.magnitude(x, 0f, 9.81f, time), time / 1_000_000)) triggers++
        }
        assertTrue(triggers > 0)
    }

    @Test fun duplicateOrReversedTimeDoesNotAddPeaks() {
        val detector = ShakeDetector()
        detector.add(10f, 100)
        assertFalse(detector.add(0f, 100))
        assertFalse(detector.add(0f, 50))
        assertEquals(0, detector.count)
    }

    @Test fun unevenTrainingShakesRemainReproducibleWithSavedSensitivity() {
        val calibration = ShakeCalibration()
        val amplitudes = listOf(3.5f, 10f, 10f)
        val trace = (0..44).map { i ->
            i * 40L to if (i % 15 in 1..3) amplitudes[i / 15] else 0f
        }
        assertEquals(1, trace.count { (time, value) -> calibration.add(value, time) })
        val monitoring = ShakeDetector(threshold = calibration.threshold)
        assertEquals(1, trace.count { (time, value) -> monitoring.add(value, time) })
    }

    @Test fun sensorInterruptionDiscardsUnfinishedSequence() {
        val detector = ShakeDetector()
        detector.add(8f, 0); detector.add(0f, 100)
        detector.add(8f, 400); detector.add(0f, 500)
        detector.add(8f, 1600)
        assertFalse(detector.add(0f, 1700))
        assertEquals(1, detector.count)
    }

    @Test fun invalidRawSampleDoesNotPoisonGravityFilter() {
        val filter = MotionFilter()
        filter.magnitude(0f, 0f, 9.81f, 0)
        assertEquals(0f, filter.magnitude(Float.NaN, 0f, 9.81f, 40_000_000), 0.01f)
        assertEquals(0f, filter.magnitude(0f, 0f, 9.81f, 80_000_000), 0.01f)
        assertTrue(filter.magnitude(8f, 0f, 9.81f, 120_000_000) > 3f)
    }
}
