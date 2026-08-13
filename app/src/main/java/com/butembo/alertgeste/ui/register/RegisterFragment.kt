package com.butembo.alertgeste.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.butembo.alertgeste.R
import com.butembo.alertgeste.data.local.entity.Utilisateur
import com.butembo.alertgeste.data.repository.AlertGesteRepository
import com.butembo.alertgeste.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var repository: AlertGesteRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            val nom = binding.etNom.text.toString().trim()
            val tel = binding.etTel.text.toString().trim()
            val msg = binding.etMessage.text.toString().trim()

            if (nom.isEmpty() || tel.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val user = Utilisateur(nom = nom, telephone = tel, messageAlerte = msg)
                repository.enregistrerUtilisateur(user)
                findNavController().navigate(R.id.action_register_to_dashboard)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
