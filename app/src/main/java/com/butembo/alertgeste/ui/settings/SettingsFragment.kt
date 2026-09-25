package com.butembo.alertgeste.ui.settings

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.FragmentSettingsBinding
import com.butembo.alertgeste.service.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    @Inject lateinit var repository: AlertGesteRepository
    private val backgroundPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { updateBackgroundLabel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = _binding!!
        binding.btnSave.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                repository.getUtilisateurOnce()?.let { user ->
                    if (savedInstanceState == null) {
                        binding.etNom.setText(user.nom); binding.etTel.setText(user.telephone); binding.etMessage.setText(user.messageAlerte)
                    }
                    binding.btnSave.isEnabled = true
                }
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) { message("Chargement impossible.") }
        }
        binding.btnSave.setOnClickListener {
            val name = binding.etNom.text.toString()
            val phone = binding.etTel.text.toString()
            val text = binding.etMessage.text.toString()
            binding.btnSave.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val user = repository.getUtilisateurOnce() ?: error("Profil introuvable.")
                    repository.updateUtilisateur(user.copy(nom = name, telephone = phone, messageAlerte = text))
                    message("Paramètres enregistrés.")
                } catch (e: CancellationException) { throw e }
                catch (e: Exception) { message(e.message ?: "Sauvegarde impossible.") }
                finally { _binding?.btnSave?.isEnabled = true }
            }
        }
        binding.switchReliable.isChecked = DeviceReadiness.allowWakeLock(requireContext())
        binding.switchReliable.isEnabled = DeviceReadiness.wakeUpSensor(requireContext()) == null
        binding.switchReliable.setOnCheckedChangeListener { _, checked ->
            DeviceReadiness.setAllowWakeLock(requireContext(), checked)
        }
        binding.btnBackground.setOnClickListener {
            if (Build.VERSION.SDK_INT < 29) return@setOnClickListener
            if (!DeviceReadiness.hasLocation(requireContext())) {
                message("Activez d’abord la surveillance pour autoriser la localisation.")
                return@setOnClickListener
            }
            AlertDialog.Builder(requireContext()).setTitle("Reprise après redémarrage")
                .setMessage("Pour reprendre la surveillance sans ouvrir l’application après un redémarrage, Android demande la localisation « Toujours autoriser ». AlertGeste recherche votre position uniquement lors d’une alerte et la transmet par SMS à vos contacts. Cette autorisation est facultative : sinon, réactivez la surveillance en ouvrant l’application.")
                .setPositiveButton("Continuer") { _, _ ->
                    if (Build.VERSION.SDK_INT == 29) backgroundPermission.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    else startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${requireContext().packageName}")))
                }.setNegativeButton("Annuler", null).show()
        }
        binding.btnPrivacy.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Données et fonctionnement")
                .setMessage("Vos coordonnées, contacts et 200 dernières alertes sont conservés sur cet appareil. Aucune donnée n’est envoyée à un serveur AlertGeste. Lors d’une alerte, le message, votre identité et la position disponible sont transmis à vos contacts par votre opérateur SMS. Le lien de carte s’ouvre chez Google si le destinataire le consulte. La localisation utilise les services Android et les réglages du téléphone. Les sauvegardes Android des données de l’application sont désactivées.\n\nLes SMS peuvent être facturés et visibles dans l’application SMS. Un envoi confirmé par l’opérateur ne garantit pas sa réception. Sans réseau, SIM ou crédit, l’envoi peut échouer. Aucun renvoi automatique n’est effectué. Une fermeture forcée désactive la protection jusqu’à sa réactivation.\n\nLa réinitialisation efface les données locales ; elle ne rappelle pas les SMS déjà transmis.")
                .setPositiveButton("Fermer", null).show()
        }
        binding.btnResetApp.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Effacer toutes les données ?")
                .setMessage("La surveillance sera arrêtée. Le profil, les contacts, le geste et l’historique seront supprimés. Les SMS déjà transmis ne peuvent pas être rappelés.")
                .setNegativeButton("Annuler", null).setPositiveButton("Tout effacer") { _, _ ->
                    binding.btnResetApp.isEnabled = false
                    viewLifecycleOwner.lifecycleScope.launch {
                        try {
                            repository.setSurveillance(false)
                            requireContext().stopService(Intent(requireContext(), SurveillanceService::class.java))
                            withTimeout(5000) { SurveillanceState.state.first { !it.running } }
                            repository.reinitialiserCompte()
                            requireContext().getSharedPreferences("power", 0).edit().clear().apply()
                            AlertNotifications(requireContext()).clearAll()
                            findNavController().navigate(R.id.registerFragment, null, navOptions {
                                popUpTo(R.id.nav_graph) { inclusive = true }
                            })
                        } catch (_: TimeoutCancellationException) {
                            message("Arrêt du service en cours. Réessayez la réinitialisation.")
                            _binding?.btnResetApp?.isEnabled = true
                        } catch (e: CancellationException) { throw e }
                        catch (_: Exception) { message("Réinitialisation incomplète. Réessayez."); _binding?.btnResetApp?.isEnabled = true }
                    }
                }.show()
        }
    }
    override fun onResume() { super.onResume(); updateBackgroundLabel() }
    private fun updateBackgroundLabel() {
        _binding?.btnBackground?.text = if (Build.VERSION.SDK_INT < 29 ||
            DeviceReadiness.granted(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION))
            "Reprise au redémarrage : autorisation accordée" else "Configurer la reprise au redémarrage"
    }
    private fun message(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    override fun onDestroyView() { _binding = null; super.onDestroyView() }
}
