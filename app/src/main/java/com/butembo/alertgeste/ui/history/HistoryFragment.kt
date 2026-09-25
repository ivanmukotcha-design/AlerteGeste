package com.butembo.alertgeste.ui.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AlertDialog
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Alerte
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.butembo.alertgeste.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val viewModel: HistoryViewModel by viewModels()
    private var deleteDialog: AlertDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = _binding!!
        val adapter = HistoryAdapter(::confirmDelete)
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.alertes.collect {
                        adapter.submitList(it)
                        binding.emptyState.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    viewModel.message.collect { message ->
                        if (message != null) {
                            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                            viewModel.messageShown()
                        }
                    }
                }
            }
        }
    }
    private fun confirmDelete(alerte: Alerte) {
        if (deleteDialog != null) return
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(alerte.horodatage))
        deleteDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.history_delete_title)
            .setMessage(getString(R.string.history_delete_confirmation, date))
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.history_delete_action) { _, _ -> viewModel.supprimerAlerte(alerte) }
            .setOnDismissListener { deleteDialog = null }
            .show()
    }

    override fun onDestroyView() {
        deleteDialog?.dismiss()
        deleteDialog = null
        _binding?.rvHistory?.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
