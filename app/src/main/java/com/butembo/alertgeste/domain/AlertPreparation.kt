package com.butembo.alertgeste.domain

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive

/** Runs in the caller's job. Cancelling during either stage prevents the subsequent submission. */
class AlertPreparation(private val feedback: CountdownFeedback? = null) {
    suspend fun <T> run(
        onCountdown: suspend (Int) -> Unit,
        onLocating: suspend () -> Unit,
        locate: suspend () -> T
    ): T {
        try {
            for (seconds in 5 downTo 1) {
                currentCoroutineContext().ensureActive()
                feedback?.tick(seconds)
                onCountdown(seconds)
                delay(1000)
            }
        } finally {
            feedback?.stop()
        }
        currentCoroutineContext().ensureActive()
        onLocating()
        val result = locate()
        currentCoroutineContext().ensureActive()
        return result
    }
}

interface CountdownFeedback {
    fun tick(seconds: Int)
    fun stop()
}
