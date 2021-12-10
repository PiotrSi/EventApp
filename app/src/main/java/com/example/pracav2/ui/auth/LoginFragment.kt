package com.example.pracav2.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentLoginBinding
import com.example.pracav2.ui.enable
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.HomeActivity
import com.example.pracav2.ui.startNewActivity
import com.example.pracav2.ui.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z]{1,50}\\.[a-zA-Z0-9]{1,50}"+
                "\\@pollub\\.edu\\.pl"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.progressbar.visible(false)
        binding.loginButton.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
//                            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb3NzcyIsImlhdCI6MTYzNzYxMDgwMH0.MasJGXTrE3Rwo61wsmCTHd-r6Wpsin5TvTeLi-z8UuAWmMeBZ4dLavMUmzpYxs5DwHXPFe-ToZP3q0uYHDJ2Sw"
                            it.value.accessToken!!
                            ,
//                            it.value.refreshToken!!
                            "sad6s7a85fds6g7df8s7"
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
            }
        })

        binding.password.addTextChangedListener {
            val email = binding.login.text.toString().trim()
            binding.loginButton.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }
        binding.signup.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.login.text.toString().trim()
       if(isValidEmail(email)) {
           val password = binding.password.text.toString().trim()
           val jsonObjectString = "{\"username\":\"$email\" , \"password\":\"$password\"}"
           val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
           viewModel.login(requestBody)
       }else Snackbar.make(requireView(),"invalid email",Snackbar.LENGTH_SHORT).show()
    }

    private fun isValidEmail(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
}