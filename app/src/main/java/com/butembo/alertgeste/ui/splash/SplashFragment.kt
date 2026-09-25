package com.butembo.alertgeste.ui.splash

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    @Inject lateinit var repository: AlertGesteRepository
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                if (!com.butembo.alertgeste.service.SurveillanceState.state.value.running) {
                    repository.recoverInterrupted()
                    com.butembo.alertgeste.service.AlertNotifications(requireContext()).clearProgress()
                }
                val user = repository.getUtilisateurOnce()
                findNavController().navigate(if (user == null) R.id.action_splash_to_register else R.id.action_splash_to_dashboard)
            } catch (e: CancellationException) { throw e }
            catch (_: Exception) {
                Toast.makeText(requireContext(), "Données indisponibles. Touchez l’écran pour réessayer.", Toast.LENGTH_LONG).show()
                view.setOnClickListener { onViewCreated(view, savedInstanceState) }
            }
        }
    }
}
