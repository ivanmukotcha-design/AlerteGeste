package com.butembo.alertgeste.ui.register

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Utilisateur
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    @Inject lateinit var repository: AlertGesteRepository
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = _binding!!
        binding.btnSave.setOnClickListener {
            val user = Utilisateur(nom = binding.etNom.text.toString(), telephone = binding.etTel.text.toString(), messageAlerte = binding.etMessage.text.toString())
            binding.btnSave.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    repository.enregistrerUtilisateur(user)
                    findNavController().navigate(R.id.action_register_to_dashboard)
                } catch (e: CancellationException) { throw e }
                catch (e: Exception) { Toast.makeText(requireContext(), e.message ?: "Enregistrement impossible.", Toast.LENGTH_LONG).show() }
                finally { _binding?.btnSave?.isEnabled = true }
            }
        }
    }
    override fun onDestroyView() { _binding = null; super.onDestroyView() }
}