package com.butembo.alertgeste.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.databinding.FragmentDashboardBinding
import com.butembo.alertgeste.service.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private var pendingSos = false
    private val permissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (_binding != null) startIfReady(pendingSos)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        pendingSos = savedInstanceState?.getBoolean("pending_sos") ?: false
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnToggleSurveillance.setOnClickListener {
            if (SurveillanceState.state.value.monitoring) SurveillanceService.arreter(requireContext())
            else requestStart(false)
        }
        binding.btnSosManuel.setOnClickListener { requestStart(true) }
        binding.btnCancelAlert.setOnClickListener { SurveillanceService.annuler(requireContext()) }
        binding.btnContacts.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_contacts) }
        binding.btnGesture.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_gesture) }
        binding.btnHistory.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_history) }
        binding.btnSettings.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_settings) }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(viewModel.uiState, SurveillanceState.state) { profile, runtime -> profile to runtime }
                    .collect { (profile, runtime) ->
                        binding.tvStatusText.text = when {
                            runtime.monitoring && runtime.training -> "Calibration — surveillance suspendue"
                            runtime.monitoring -> getString(R.string.surveillance_active)
                            else -> getString(R.string.surveillance_inactive)
                        }
                        binding.tvReadiness.text = buildString {
                            append("${profile.nombreContacts} contact(s) • ")
                            append(if (profile.profilEnregistre) "Geste configuré" else "Geste à configurer")
                            append("\n")
                            append(runtime.message)
                            if (runtime.monitoring && DeviceReadiness.wakeUpSensor(requireContext()) == null) {
                                append(if (DeviceReadiness.allowWakeLock(requireContext())) "\nProtection en veille : consommation accrue sur cet appareil."
                                else "\nMode économie : la détection peut s’interrompre en veille.")
                            }
                        }
                        binding.btnToggleSurveillance.text = getString(if (runtime.monitoring) R.string.desactiver_surveillance else R.string.activer_surveillance)
                        binding.btnToggleSurveillance.isEnabled = profile.nomUtilisateur.isNotEmpty()
                        binding.btnSosManuel.isEnabled = profile.nomUtilisateur.isNotEmpty() && runtime.alertPhase == null && !runtime.training
                        binding.btnGesture.isEnabled = runtime.alertPhase == null
                        binding.btnCancelAlert.visibility = if (runtime.alertPhase in listOf("COMPTE_A_REBOURS", "LOCALISATION")) View.VISIBLE else View.GONE
                    }
            }
        }
    }

    private fun requestStart(sos: Boolean) {
        val state = viewModel.uiState.value
        if (state.nombreContacts == 0) return message("Ajoutez au moins un contact de confiance.")
        if (!sos && !state.profilEnregistre) return message("Configurez d’abord votre geste.")
        pendingSos = sos
        val missing = DeviceReadiness.permissions().filter {
            when {
                it.contains("LOCATION") && DeviceReadiness.hasLocation(requireContext()) -> false
                else -> !DeviceReadiness.granted(requireContext(), it)
            }
        }
        if (missing.isNotEmpty()) {
            AlertDialog.Builder(requireContext()).setTitle("Autorisations pour les alertes")
                .setMessage("AlertGeste envoie automatiquement votre message et votre position aux contacts choisis après un SOS ou un geste, même lorsque l’application n’est pas affichée. La position est recherchée uniquement lors d’une alerte. Les SMS peuvent être facturés par votre opérateur. Les notifications permettent d’annuler avant transmission.")
                .setPositiveButton("Continuer") { _, _ -> permissions.launch(missing.toTypedArray()) }
                .setNegativeButton("Annuler", null).show()
        } else startIfReady(sos)
    }

    private fun startIfReady(sos: Boolean) {
        DeviceReadiness.problem(requireContext(), requireLocation = !sos)?.let { problem ->
            AlertDialog.Builder(requireContext()).setTitle("Action nécessaire").setMessage(problem)
                .setPositiveButton("Paramètres") { _, _ ->
                    val intent = if (!DeviceReadiness.locationEnabled(requireContext())) Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    else Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${requireContext().packageName}"))
                    startActivity(intent)
                }.setNegativeButton("Fermer", null).show()
            return
        }
        val powerPrefs = requireContext().getSharedPreferences("power", 0)
        if (!sos && DeviceReadiness.wakeUpSensor(requireContext()) == null && !powerPrefs.getBoolean("chosen", false)) {
            AlertDialog.Builder(requireContext()).setTitle("Détection écran éteint")
                .setMessage("Cet appareil ne fournit pas de capteur de réveil. La protection en veille maintient le processeur éveillé et consomme davantage. Le mode économie peut manquer des gestes écran éteint. Vous pourrez changer ce choix dans les paramètres.")
                .setPositiveButton("Protection en veille") { _, _ ->
                    DeviceReadiness.setAllowWakeLock(requireContext(), true)
                    powerPrefs.edit().putBoolean("chosen", true).apply()
                    startService(sos)
                }.setNeutralButton("Économie") { _, _ ->
                    DeviceReadiness.setAllowWakeLock(requireContext(), false)
                    powerPrefs.edit().putBoolean("chosen", true).apply()
                    startService(sos)
                }.setNegativeButton("Annuler", null).show()
        } else startService(sos)
    }
    private fun startService(sos: Boolean) {
        val result = if (sos) SurveillanceService.declencherSos(requireContext()) else SurveillanceService.demarrer(requireContext())
        result.onFailure { message(it.message ?: "Démarrage impossible.") }
    }
    private fun message(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    override fun onSaveInstanceState(outState: Bundle) { outState.putBoolean("pending_sos", pendingSos); super.onSaveInstanceState(outState) }
    override fun onDestroyView() { _binding = null; super.onDestroyView() }
}
