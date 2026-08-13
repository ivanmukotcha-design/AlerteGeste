package com.butembo.alertgeste.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SplashDestination { REGISTER, DASHBOARD }

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500L)
            val utilisateur = repository.getUtilisateurOnce()
            _destination.value = if (utilisateur != null) {
                SplashDestination.DASHBOARD
            } else {
                SplashDestination.REGISTER
            }
        }
    }
}
