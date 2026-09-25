package com.butembo.alertgeste.ui.gesture

import android.hardware.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.databinding.FragmentGestureBinding
import com.butembo.alertgeste.domain.MotionFilter
import com.butembo.alertgeste.service.SurveillanceState
import com.butembo.alertgeste.service.PhoneHaptics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@AndroidEntryPoint
class GestureFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentGestureBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GestureViewModel by viewModels()
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private val filter = MotionFilter()
    private var sensorResponding = false
    private var sensorWatchdog: Job? = null
    private lateinit var haptics: PhoneHaptics
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGestureBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sensorManager = requireContext().getSystemService(SensorManager::class.java)
        haptics = PhoneHaptics(requireContext())
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER, true)
            ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        binding.btnResetCalibration.isEnabled = false
        binding.btnResetCalibration.setOnClickListener {
            haptics.stop()
            filter.reset()
            viewModel.commencerEntrainement()
        }
        binding.btnDone.setOnClickListener { findNavController().navigateUp() }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect { state ->
                    binding.tvCount.text = getString(R.string.gesture_count, state.secoussesDetectees)
                    binding.instructions.text = state.message
                    binding.btnResetCalibration.isEnabled = sensorResponding && !state.saving
                    binding.btnResetCalibration.setText(when {
                        state.saving -> R.string.saving_calibration
                        state.etape == GestureEtape.REPOS -> R.string.start_calibration
                        else -> R.string.retry_calibration
                    })
                    binding.btnDone.visibility = if (state.sauvegardeFait) View.VISIBLE else View.GONE
                    if (state.sauvegardeFait && viewModel.consumeSaveConfirmation()) haptics.gestureSaved()
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        SurveillanceState.training.value = true
        filter.reset()
        sensorResponding = false
        binding.btnResetCalibration.isEnabled = false
        binding.sensorStatus.setText(R.string.sensor_connecting)
        val registered = sensor?.let { sensorManager.registerListener(this, it, 40_000) } ?: false
        if (!registered) binding.sensorStatus.setText(R.string.sensor_unavailable)
        else sensorWatchdog = viewLifecycleOwner.lifecycleScope.launch {
            delay(2500)
            if (!sensorResponding) binding.sensorStatus.setText(R.string.sensor_unavailable)
        }
    }
    override fun onPause() {
        sensorManager.unregisterListener(this)
        sensorWatchdog?.cancel()
        haptics.stop()
        viewModel.interrompre()
        SurveillanceState.training.value = false
        super.onPause()
    }
    override fun onSensorChanged(event: SensorEvent) {
        val b = _binding ?: return
        if (!sensorResponding) {
            sensorResponding = true
            b.sensorStatus.setText(R.string.sensor_ready)
            b.btnResetCalibration.isEnabled = !viewModel.uiState.value.saving
        }
        val magnitude = filter.magnitude(event.values[0], event.values[1], event.values[2], event.timestamp)
        b.progressMagnitude.progress = (magnitude * 6).toInt().coerceIn(0, 100)
        viewModel.onValeurAccelerometre(magnitude, event.timestamp / 1_000_000)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    override fun onDestroyView() { _binding = null; super.onDestroyView() }
}
