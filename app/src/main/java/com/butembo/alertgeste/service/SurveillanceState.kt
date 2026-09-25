package com.butembo.alertgeste.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SurveillanceUiState(
    val running: Boolean = false,
    val monitoring: Boolean = false,
    val training: Boolean = false,
    val alertPhase: String? = null,
    val message: String = ""
)

object SurveillanceState {
    private val mutable = MutableStateFlow(SurveillanceUiState())
    val state = mutable.asStateFlow()
    val training = MutableStateFlow(false)
    fun update(transform: (SurveillanceUiState) -> SurveillanceUiState) { mutable.value = transform(mutable.value) }
}