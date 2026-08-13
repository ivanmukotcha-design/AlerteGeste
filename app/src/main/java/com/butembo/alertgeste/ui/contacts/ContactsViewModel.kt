package com.butembo.alertgeste.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: AlertGesteRepository
) : ViewModel() {

    val contacts = repository.getTousContacts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun ajouterContact(contact: Contact) {
        viewModelScope.launch { repository.ajouterContact(contact) }
    }

    fun mettreAJourContact(contact: Contact) {
        viewModelScope.launch { repository.mettreAJourContact(contact) }
    }

    fun supprimerContact(contact: Contact) {
        viewModelScope.launch { repository.supprimerContact(contact) }
    }
}
