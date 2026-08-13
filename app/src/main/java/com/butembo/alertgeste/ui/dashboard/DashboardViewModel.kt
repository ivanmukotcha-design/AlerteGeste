package com.butembo.alertgeste.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.local.entity.GesteProfil
import com.butembo.alertgeste.data.local.entity.Utilisateur
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val nomUtilisateur: String = "",
    val surveillanceActive: Boolean = false,
    val profilEnregistre: Boolean = false,
    val nombreContacts: Int = 0
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getUtilisateur(),
        repository.getProfilActif(),
        repository.getTousContacts()
    ) { utilisateur: Utilisateur?, profil: GesteProfil?, contacts: List<Contact> ->
        DashboardUiState(
            nomUtilisateur = utilisateur?.nom ?: "",
            surveillanceActive = utilisateur?.surveillanceActive ?: false,
            profilEnregistre = profil?.estEnregistre == true,
            nombreContacts = contacts.size
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState())

    fun toggleSurveillance(active: Boolean) {
        viewModelScope.launch {
            val utilisateur = repository.getUtilisateurOnce() ?: return@launch
            repository.updateUtilisateur(utilisateur.copy(surveillanceActive = active))
        }
    }
}
