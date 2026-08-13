package com.butembo.alertgeste.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    val alertes = repository.getToutesAlertes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun supprimerAlerte(alerte: Alerte) {
        viewModelScope.launch { repository.supprimerAlerte(alerte) }
    }
}
