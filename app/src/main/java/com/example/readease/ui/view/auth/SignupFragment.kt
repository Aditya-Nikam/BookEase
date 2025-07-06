package com.example.readease.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.readease.R
import com.example.readease.data.db.AppDatabase
import com.example.readease.data.model.User
import com.example.readease.databinding.FragmentSignupBinding
import com.example.readease.repository.UserRepository
import com.example.readease.ui.view.MainActivity
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val repository = UserRepository(userDao)

        binding.btnCreateAccount.setOnClickListener {
            val emailStr = binding.etEmail.text.toString().trim()
            val passwordStr = binding.etPassword.text.toString().trim()

            if (emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val existingUser = repository.getUserByEmail(emailStr)
                    if (existingUser != null) {
                        Toast.makeText(requireContext(), "User already exists", Toast.LENGTH_SHORT).show()
                    } else {
                        repository.register(User(email = emailStr, password = passwordStr))
                        Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment2_to_loginFragment2)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
