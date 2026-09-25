package com.butembo.alertgeste.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.databinding.DialogContactFormBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactFormDialog : BottomSheetDialogFragment() {

    private var _binding: DialogContactFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactsViewModel by viewModels({ requireParentFragment() })

    companion object {
        private const val ARG_CONTACT_ID = "contact_id"
        private const val ARG_NOM = "nom"
        private const val ARG_TEL = "tel"
        private const val ARG_RELATION = "relation"

        fun newInstance(contact: Contact?) = ContactFormDialog().apply {
            arguments = Bundle().apply {
                contact?.let {
                    putLong(ARG_CONTACT_ID, it.id)
                    putString(ARG_NOM, it.nom)
                    putString(ARG_TEL, it.telephone)
                    putString(ARG_RELATION, it.relation)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogContactFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val existingId = arguments?.getLong(ARG_CONTACT_ID, -1L) ?: -1L
        val isEdit = existingId > 0

        binding.tvTitreDialog.text = if (isEdit) "Modifier le contact" else "Ajouter un contact"
        binding.etNomContact.setText(arguments?.getString(ARG_NOM) ?: "")
        binding.etTelContact.setText(arguments?.getString(ARG_TEL) ?: "")
        binding.etRelationContact.setText(arguments?.getString(ARG_RELATION) ?: "")

        binding.btnSauvegarder.setOnClickListener {
            val nom = binding.etNomContact.text.toString().trim()
            val tel = binding.etTelContact.text.toString().trim()
            val relation = binding.etRelationContact.text.toString().trim()

            if (nom.isEmpty() || tel.isEmpty()) {
                Toast.makeText(requireContext(), "Nom et téléphone obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val contact = Contact(
                id = if (isEdit) existingId else 0,
                nom = nom,
                telephone = tel,
                relation = relation
            )

            binding.btnSauvegarder.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    viewModel.save(contact)
                    dismiss()
                } catch (e: CancellationException) { throw e }
                catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message ?: "Sauvegarde impossible", Toast.LENGTH_LONG).show()
                } finally { _binding?.btnSauvegarder?.isEnabled = true }
            }
        }

        binding.btnAnnuler.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
