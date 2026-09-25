package com.butembo.alertgeste.ui.contacts

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val viewModel: ContactsViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = _binding!!
        val adapter = ContactAdapter({ edit(it) }, { contact ->
            AlertDialog.Builder(requireContext()).setTitle("Supprimer ce contact ?")
                .setMessage(contact.nom).setNegativeButton("Annuler", null)
                .setPositiveButton("Supprimer") { _, _ ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        try { viewModel.delete(contact) }
                        catch (e: CancellationException) { throw e }
                        catch (_: Exception) { Toast.makeText(requireContext(), "Suppression impossible.", Toast.LENGTH_LONG).show() }
                    }
                }.show()
        })
        binding.rvContacts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = adapter
        binding.btnAddContact.setOnClickListener { edit(null) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect {
                    adapter.submitList(it)
                    binding.title.text = "Vos proches (${it.size}/10)"
                    binding.emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    binding.btnAddContact.isEnabled = it.size < 10
                }
            }
        }
    }
    private fun edit(contact: Contact?) {
        if (childFragmentManager.findFragmentByTag("contact_form") == null)
            ContactFormDialog.newInstance(contact).show(childFragmentManager, "contact_form")
    }
    override fun onDestroyView() { _binding?.rvContacts?.adapter = null; _binding = null; super.onDestroyView() }
}
