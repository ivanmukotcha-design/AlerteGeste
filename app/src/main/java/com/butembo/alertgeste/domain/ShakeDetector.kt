package com.butembo.alertgeste.domain

import kotlin.math.sqrt
import kotlin.math.max

/** Pure detector, shared by calibration and surveillance. Timestamps are monotonic milliseconds. */
class ShakeDetector(
    private val threshold: Float = 5f,
    private val repetitions: Int = 3,
    private val windowMs: Long = 2500,
    private val minIntervalMs: Long = 250
) {
    private val peaks = ArrayDeque<Long>()
    private var rising = true
    private var extreme = 0f
    private var lastPeak = Long.MIN_VALUE
    private var lastSample = Long.MIN_VALUE
    var lastPeakMagnitude = 0f
        private set
    var acceptedPeaks = 0
        private set
    var count: Int = 0
        private set

    fun add(magnitude: Float, timestampMs: Long): Boolean {
        if (!magnitude.isFinite() || magnitude < 0 || timestampMs < 0 || timestampMs <= lastSample) return false
        if (lastSample != Long.MIN_VALUE && timestampMs - lastSample > 1000) {
            peaks.clear()
            lastPeak = Long.MIN_VALUE
            rising = true
            extreme = 0f
        }
        lastSample = timestampMs
        while (peaks.isNotEmpty() && timestampMs - peaks.first() > windowMs) peaks.removeFirst()
        count = peaks.size
        // Count a peak only after a meaningful fall. Continuous shaking need not
        // return to absolute rest, while a plateau or sensor noise is not a shake.
        if (!rising) {
            extreme = minOf(extreme, magnitude)
            if (magnitude - extreme < max(1f, threshold * 0.25f)) return false
            rising = true
            extreme = magnitude
            return false
        }
        extreme = max(extreme, magnitude)
        if (extreme < threshold || extreme - magnitude < max(1f, extreme * 0.25f)) return false
        val peak = extreme
        rising = false
        extreme = magnitude
        if (lastPeak != Long.MIN_VALUE && timestampMs - lastPeak < minIntervalMs) return false
        lastPeak = timestampMs
        lastPeakMagnitude = peak
        acceptedPeaks++
        peaks.addLast(timestampMs)
        count = peaks.size
        if (count < repetitions) return false
        peaks.clear()
        return true
    }

    fun reset() {
        peaks.clear()
        count = 0
        rising = true
        extreme = 0f
        lastPeak = Long.MIN_VALUE
        lastSample = Long.MIN_VALUE
        lastPeakMagnitude = 0f
        acceptedPeaks = 0
    }
}

/** Calibration starts gently and learns a bounded threshold from three recent peaks. */
class ShakeCalibration {
    private val detector = ShakeDetector(threshold = 3f)
    private val amplitudes = ArrayDeque<Float>()
    val count get() = detector.count
    var threshold = 3f
        private set

    fun add(magnitude: Float, timestampMs: Long): Boolean {
        val previous = detector.acceptedPeaks
        val complete = detector.add(magnitude, timestampMs)
        if (detector.acceptedPeaks != previous) {
            amplitudes.addLast(detector.lastPeakMagnitude)
            while (amplitudes.size > 3) amplitudes.removeFirst()
        }
        if (complete) {
            // Keep even the gentlest accepted shake reproducible after saving.
            threshold = minOf(amplitudes.sorted()[1] * 0.65f, amplitudes.min() * 0.8f).coerceIn(3f, 12f)
        }
        return complete
    }
}

/** Initialize gravity from the first sample: placing a motionless phone must not count as a shake. */
class MotionFilter {
    private val gravity = FloatArray(3)
    private var initialized = false
    private var lastTimestamp = 0L

    fun magnitude(x: Float, y: Float, z: Float, timestampNanos: Long): Float {
        if (!x.isFinite() || !y.isFinite() || !z.isFinite() || timestampNanos < 0 ||
            (initialized && timestampNanos <= lastTimestamp)) return 0f
        if (!initialized || timestampNanos - lastTimestamp > 1_000_000_000L) {
            gravity[0] = x; gravity[1] = y; gravity[2] = z
            initialized = true
            lastTimestamp = timestampNanos
            return 0f
        }
        val dt = ((timestampNanos - lastTimestamp) / 1_000_000_000f).coerceIn(0.001f, 1f)
        val alpha = 0.25f / (0.25f + dt)
        lastTimestamp = timestampNanos
        gravity[0] = alpha * gravity[0] + (1 - alpha) * x
        gravity[1] = alpha * gravity[1] + (1 - alpha) * y
        gravity[2] = alpha * gravity[2] + (1 - alpha) * z
        val dx = x - gravity[0]; val dy = y - gravity[1]; val dz = z - gravity[2]
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    fun reset() { initialized = false }
}
