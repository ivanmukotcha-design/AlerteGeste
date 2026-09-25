package com.butembo.alertgeste.ui.gesture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.domain.ShakeCalibration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class GestureEtape { REPOS, EN_COURS, TERMINE }
data class GestureUiState(
    val etape: GestureEtape = GestureEtape.REPOS,
    val secoussesDetectees: Int = 0,
    val sauvegardeFait: Boolean = false,
    val message: String = "Tenez bien votre téléphone, puis appuyez sur Commencer.",
    val saving: Boolean = false
)

@HiltViewModel
class GestureViewModel @Inject constructor(private val repository: AlertGesteRepository) : ViewModel() {
    private val mutable = MutableStateFlow(GestureUiState())
    val uiState = mutable.asStateFlow()
    private var detector = ShakeCalibration()
    private var startedAt = 0L
    private var saveConfirmationPending = false
    fun consumeSaveConfirmation(): Boolean {
        val pending = saveConfirmationPending
        saveConfirmationPending = false
        return pending
    }
    fun commencerEntrainement() {
        if (mutable.value.saving) return
        detector = ShakeCalibration()
        saveConfirmationPending = false
        startedAt = 0L
        mutable.value = GestureUiState(etape = GestureEtape.EN_COURS,
            message = "Faites 3 secousses rapprochées, sans forcer. Inutile de vous arrêter entre chaque mouvement.")
    }
    fun interrompre() {
        if (mutable.value.etape == GestureEtape.EN_COURS) mutable.value = GestureUiState(
            message = "Configuration en pause. Appuyez sur Commencer pour reprendre.")
    }
    fun onValeurAccelerometre(magnitude: Float, timestampMs: Long) {
        if (mutable.value.etape != GestureEtape.EN_COURS) return
        if (startedAt == 0L) startedAt = timestampMs
        val complete = detector.add(magnitude, timestampMs)
        mutable.value = mutable.value.copy(secoussesDetectees = detector.count,
            message = if (timestampMs - startedAt > 8000L)
                "La jauge réagit ? Faites 3 allers-retours rapprochés du poignet. Les 3 secousses doivent tenir en 2,5 secondes."
            else mutable.value.message)
        if (!complete) return
        mutable.value = mutable.value.copy(etape = GestureEtape.TERMINE, saving = true)
        viewModelScope.launch {
            try {
                repository.sauvegarderProfil(GesteProfil(seuilMin = detector.threshold,
                    seuilMax = Float.MAX_VALUE, fenetreTempsMs = 2500L, estEnregistre = true))
                saveConfirmationPending = true
                mutable.value = mutable.value.copy(sauvegardeFait = true, saving = false,
                    message = "Geste enregistré ! La sensibilité est adaptée à vos mouvements. Vous pouvez revenir à l’accueil et activer la surveillance.")
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) {
                mutable.value = mutable.value.copy(saving = false, message = "Échec de sauvegarde. Recommencez.")
            }
        }
    }
}
