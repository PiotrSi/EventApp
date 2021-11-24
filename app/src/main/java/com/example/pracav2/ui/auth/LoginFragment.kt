package com.example.pracav2.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentLoginBinding
import com.example.pracav2.ui.enable
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.HomeActivity
import com.example.pracav2.ui.startNewActivity
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

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

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.login.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val jsonObjectString = "{\"username\":\"$email\" , \"password\":\"$password\"}"
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        viewModel.login(requestBody)
    }
}