package com.butembo.alertgeste.ui.gesture

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.butembo.alertgeste.databinding.FragmentGestureBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@AndroidEntryPoint
class GestureFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentGestureBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GestureViewModel by viewModels()
    
    private lateinit var sensorManager: SensorManager
    private var accelerometre: Sensor? = null
    
    private val alpha = 0.8f
    private var gravite = FloatArray(3) { 0f }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGestureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometre == null) {
            Toast.makeText(requireContext(), "Accéléromètre non disponible sur cet appareil", Toast.LENGTH_LONG).show()
        }

        setupObservers()
        
        binding.btnResetCalibration.setOnClickListener {
            viewModel.commencerEntrainement()
        }
        
        // Démarrer automatiquement l'écoute pour la jauge
        viewModel.commencerEntrainement()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.tvCount.text = "${state.secoussesDetectees}/3"
                    
                    if (state.sauvegardeFait) {
                        Toast.makeText(requireContext(), "Geste configuré et sauvegardé !", Toast.LENGTH_SHORT).show()
                    }
                    
                    if (state.etape == GestureEtape.TERMINE && !state.sauvegardeFait) {
                        viewModel.sauvegarderProfil()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometre?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        // Filtre pour enlever la gravité
        gravite[0] = alpha * gravite[0] + (1 - alpha) * event.values[0]
        gravite[1] = alpha * gravite[1] + (1 - alpha) * event.values[1]
        gravite[2] = alpha * gravite[2] + (1 - alpha) * event.values[2]

        val lx = event.values[0] - gravite[0]
        val ly = event.values[1] - gravite[1]
        val lz = event.values[2] - gravite[2]

        val magnitude = sqrt(lx * lx + ly * ly + lz * lz)
        
        // Mettre à jour la barre de progression (jauge)
        binding.progressMagnitude.progress = magnitude.toInt()
        
        // Envoyer la valeur au ViewModel pour détection
        viewModel.onValeurAccelerometre(magnitude)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
