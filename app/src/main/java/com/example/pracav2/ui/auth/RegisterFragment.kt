package com.example.pracav2.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentDescriptionBinding
import com.example.pracav2.databinding.FragmentLoginBinding
import com.example.pracav2.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    private  var department : Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)


        binding.planetsSpinner.onItemSelectedListener = this

        viewModel.getDep()
        viewModel.depResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {

                        var list = ArrayList<String>()
                        it.value.forEach {
                            list.add(it.name)
                        }
                        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,list)

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        // Apply the adapter to the spinner
                        binding.planetsSpinner.adapter = adapter

//                        }
//                        val depResponseItem : DepResponseItem = it.value.get(2)

                    }
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
//                    Toast.makeText(requireContext(), it.value.toList().toString(), Toast.LENGTH_LONG).show()

                }
                is Resource.Failure ->{
                    Toast.makeText(requireContext(), "getDep Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.regiResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {

                        view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                    }
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Failure ->{
                    Toast.makeText(requireContext(), "Registery Failure", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.signupButton.setOnClickListener {
            val email = binding.login.text.toString().trim()
            val password = binding.password.text.toString().trim()
            department+1
            val request = "{\"username\":\"$email\", \"role\" : [\"ROLE_USER\"], \"password\":\"$password\", \"depId\" : \"$department\"}"
            val requestBody = request.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.signup(requestBody)
        }

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            department = p2
//            Toast.makeText(requireContext(), p0.getItemAtPosition(p2).toString()+p3.toString()+p2.toString(), Toast.LENGTH_SHORT).show()
            p0.getItemAtPosition(p2)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}