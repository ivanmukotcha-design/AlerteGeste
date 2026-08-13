package com.butembo.alertgeste.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.Utilisateur
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    private val _inscriptionReussie = MutableStateFlow(false)
    val inscriptionReussie = _inscriptionReussie.asStateFlow()

    fun inscrire(nom: String, telephone: String, messageAlerte: String) {
        viewModelScope.launch {
            val utilisateur = Utilisateur(
                nom = nom,
                telephone = telephone,
                messageAlerte = messageAlerte,
                surveillanceActive = false
            )
            repository.sauvegarderUtilisateur(utilisateur)
            _inscriptionReussie.value = true
        }
    }
}
