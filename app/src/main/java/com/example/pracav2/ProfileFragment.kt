package com.example.pracav2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentProfileBinding
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.HomeViewModel
import com.example.pracav2.ui.logout
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        viewModel.getUserInfo()

        binding.analyzeButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAnalyzeFragment()
          findNavController().navigate(action)
        }

        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
//                    binding.progressbar.visible(false)
                    binding.email.text = it.value.username
                    binding.department.text = it.value.department
                    binding.nrSigned.text = it.value.numberSignedEvents.toString()
                    binding.averageRate.text = String.format("%.2f", it.value.averageRate)
//                    binding.averageRate.text = it.value.averageRate.toString()
                }
                is Resource.Loading -> {
//                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

}