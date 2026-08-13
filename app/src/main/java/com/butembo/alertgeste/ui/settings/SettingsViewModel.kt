package com.butembo.alertgeste.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    val utilisateur = repository.getUtilisateur()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _reinitialiseNavigation = MutableStateFlow(false)
    val reinitialiseNavigation = _reinitialiseNavigation.asStateFlow()

    fun mettreAJour(nom: String, telephone: String, messageAlerte: String) {
        viewModelScope.launch {
            val current = repository.getUtilisateurOnce() ?: return@launch
            repository.mettreAJourUtilisateur(current.copy(nom = nom, telephone = telephone, messageAlerte = messageAlerte))
        }
    }

    fun reinitialiserCompte() {
        viewModelScope.launch {
            repository.reinitialiserCompte()
            _reinitialiseNavigation.value = true
        }
    }
}
