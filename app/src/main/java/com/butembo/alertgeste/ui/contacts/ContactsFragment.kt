package com.butembo.alertgeste.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.DialogAddContactBinding
import com.butembo.alertgeste.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var repository: AlertGesteRepository
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactAdapter { contact ->
            lifecycleScope.launch { repository.supprimerContact(contact) }
        }
        binding.rvContacts.adapter = adapter

        lifecycleScope.launch {
            repository.getTousLesContacts().collect { contacts ->
                adapter.submitList(contacts)
            }
        }

        binding.btnAddContact.setOnClickListener {
            afficherDialogAjout()
        }
    }

    private fun afficherDialogAjout() {
        val dialogBinding = DialogAddContactBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSave.setOnClickListener {
            val nom = dialogBinding.etNom.text.toString()
            val tel = dialogBinding.etTel.text.toString()
            val relation = dialogBinding.etRelation.text.toString()

            if (nom.isNotEmpty() && tel.isNotEmpty()) {
                lifecycleScope.launch {
                    repository.ajouterContact(Contact(nom = nom, telephone = tel, relation = relation))
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
