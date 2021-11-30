package com.example.pracav2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.pracav2.databinding.FragmentProfileBinding
import com.example.pracav2.ui.home.HomeFragmentDirections
import com.example.pracav2.ui.logout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.analyzeButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAnalyzeFragment()
          findNavController().navigate(action)
        }


        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

}