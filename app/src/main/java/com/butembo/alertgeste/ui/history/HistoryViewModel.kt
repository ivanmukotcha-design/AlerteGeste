package com.butembo.alertgeste.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    val alertes = repository.getToutesAlertes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _message = MutableStateFlow<Int?>(null)
    val message = _message.asStateFlow()
    private var deleting = false

    fun supprimerAlerte(alerte: Alerte) {
        if (deleting) return
        deleting = true
        viewModelScope.launch {
            try {
                _message.value = if (repository.supprimerAlerte(alerte))
                    R.string.history_delete_success else R.string.history_delete_unavailable
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _message.value = R.string.history_delete_error
            } finally {
                deleting = false
            }
        }
    }

    fun messageShown() { _message.value = null }
}
