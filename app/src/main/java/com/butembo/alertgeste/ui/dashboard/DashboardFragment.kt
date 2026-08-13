package com.butembo.alertgeste.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.FragmentDashboardBinding
import com.butembo.alertgeste.service.SurveillanceService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var repository: AlertGesteRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observer l'état de surveillance
        lifecycleScope.launch {
            repository.getUtilisateur().collect { user ->
                user?.let {
                    updateUI(it.surveillanceActive)
                }
            }
        }

        binding.btnToggleSurveillance.setOnClickListener {
            lifecycleScope.launch {
                val user = repository.getUtilisateurOnce() ?: return@launch
                val newStatus = !user.surveillanceActive
                repository.updateUtilisateur(user.copy(surveillanceActive = newStatus))
                
                if (newStatus) {
                    SurveillanceService.demarrer(requireContext())
                } else {
                    SurveillanceService.arreter(requireContext())
                }
            }
        }

        // Action du bouton SOS Manuel
        binding.btnSosManuel.setOnClickListener {
            SurveillanceService.declencherSos(requireContext())
        }

        binding.btnContacts.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_contacts) }
        binding.btnGesture.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_gesture) }
        binding.btnHistory.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_history) }
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_settings) }
    }

    private fun updateUI(isActive: Boolean) {
        if (isActive) {
            binding.tvStatusText.text = getString(R.string.surveillance_active)
            binding.ivStatusIcon.setImageResource(R.drawable.ic_shield)
            binding.ivStatusIcon.setColorFilter(resources.getColor(R.color.colorSuccess, null))
            binding.btnToggleSurveillance.text = getString(R.string.desactiver_surveillance)
        } else {
            binding.tvStatusText.text = getString(R.string.surveillance_inactive)
            binding.ivStatusIcon.setImageResource(R.drawable.ic_shield)
            binding.ivStatusIcon.setColorFilter(resources.getColor(R.color.colorDanger, null))
            binding.btnToggleSurveillance.text = getString(R.string.activer_surveillance)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
