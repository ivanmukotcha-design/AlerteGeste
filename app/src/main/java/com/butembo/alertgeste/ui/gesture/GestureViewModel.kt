package com.butembo.alertgeste.ui.gesture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

enum class GestureEtape { REPOS, EN_COURS, TERMINE }

data class GestureUiState(
    val etape: GestureEtape = GestureEtape.REPOS,
    val secoussesDetectees: Int = 0,
    val seuilMin: Float = 0f,
    val seuilMax: Float = 0f,
    val sauvegardeFait: Boolean = false
)

@HiltViewModel
class GestureViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GestureUiState())
    val uiState = _uiState.asStateFlow()

    private val valeursMesures = mutableListOf<Float>()
    private var enCours = false
    private var dernierePicDetectee = 0L
    private val SEUIL_BRUT_DETECTION = 5f 

    fun commencerEntrainement() {
        valeursMesures.clear()
        enCours = true
        _uiState.value = GestureUiState(etape = GestureEtape.EN_COURS)
    }

    fun onValeurAccelerometre(magnitude: Float) {
        if (!enCours) return
        val absVal = abs(magnitude)

        if (absVal > 1f) valeursMesures.add(absVal)

        val now = System.currentTimeMillis()
        if (absVal > SEUIL_BRUT_DETECTION && now - dernierePicDetectee > 300L) {
            dernierePicDetectee = now
            val nb = _uiState.value.secoussesDetectees + 1
            _uiState.value = _uiState.value.copy(secoussesDetectees = nb)

            if (nb >= 3) {
                enCours = false
                val min = valeursMesures.minOrNull() ?: 3f
                val max = valeursMesures.maxOrNull() ?: 20f
                val seuilMin = maxOf(min * 0.8f, 3f)
                val seuilMax = max * 1.2f
                _uiState.value = _uiState.value.copy(
                    etape = GestureEtape.TERMINE,
                    seuilMin = seuilMin,
                    seuilMax = seuilMax
                )
            }
        }
    }

    fun sauvegarderProfil() {
        viewModelScope.launch {
            val state = _uiState.value
            val profil = GesteProfil(
                seuilMin = state.seuilMin,
                seuilMax = state.seuilMax,
                estEnregistre = true
            )
            repository.sauvegarderProfil(profil)
            _uiState.value = state.copy(sauvegardeFait = true)
        }
    }
}
