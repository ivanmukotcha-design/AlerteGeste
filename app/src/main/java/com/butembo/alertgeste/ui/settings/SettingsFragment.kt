package com.butembo.alertgeste.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var repository: AlertGesteRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Charger les données actuelles
        lifecycleScope.launch {
            repository.getUtilisateurOnce()?.let { user ->
                binding.etNom.setText(user.nom)
                binding.etTel.setText(user.telephone)
                binding.etMessage.setText(user.messageAlerte)
            }
        }

        binding.btnSave.setOnClickListener {
            val nom = binding.etNom.text.toString().trim()
            val tel = binding.etTel.text.toString().trim()
            val msg = binding.etMessage.text.toString().trim()

            if (nom.isEmpty() || tel.isEmpty()) {
                Toast.makeText(requireContext(), "Nom et téléphone requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val currentUser = repository.getUtilisateurOnce()
                currentUser?.let {
                    val updatedUser = it.copy(nom = nom, telephone = tel, messageAlerte = msg)
                    repository.updateUtilisateur(updatedUser)
                    Toast.makeText(requireContext(), "Paramètres mis à jour", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnResetApp.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Réinitialiser")
                .setMessage("Voulez-vous vraiment effacer toutes les données et paramètres ?")
                .setPositiveButton("Oui") { _, _ ->
                    lifecycleScope.launch {
                        // Idéalement, créer une méthode clearAll dans le repository
                        repository.effacerHistorique()
                        // On pourrait ajouter d'autres suppressions ici
                        Toast.makeText(requireContext(), "Application réinitialisée", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.splashFragment)
                    }
                }
                .setNegativeButton("Annuler", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
