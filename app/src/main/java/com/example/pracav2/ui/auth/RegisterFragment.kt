package com.example.pracav2.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +
            "(?=.*[@#$%^&+=])" +     // at least 1 special character
            "(?=\\S+$)" +            // no white spaces
            ".{6,}" +                // at least 6 characters
            "$")
    private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z]{1,50}\\.[a-zA-Z]{1,50}[0-9]{0,1}"+  //name{50}.surname{50}
                "\\@pollub\\.edu\\.pl"              // contain "@pollub.edu.pl"
    )

    private  var department : Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)



        binding.spinner.onItemSelectedListener = this

        viewModel.getDep()
        viewModel.depResponse.observe(viewLifecycleOwner, { it ->
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
                        binding.spinner.adapter = adapter
//                        }
//                        val depResponseItem : DepResponseItem = it.value.get(2)
                    }
                }
                is Resource.Failure ->{
                }
                Resource.Loading -> {

                }
            }
        })

        viewModel.regiResponse.observe(viewLifecycleOwner, {
            when(it){
                is Resource.Success ->{
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), it.value.message , Toast.LENGTH_SHORT).show()
                        view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

                    }
                }
                is Resource.Failure ->{
                    if(it.errorCode == 400)
                    Toast.makeText(requireContext(), "Email is already taken", Toast.LENGTH_SHORT).show()
                }
                Resource.Loading -> TODO()
            }
        })

        binding.signupButton.setOnClickListener {
            val email = binding.login.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val passwordR = binding.passwordRepeat.text.toString().trim()

            if(validation(email, password, passwordR)){
                department += 1
                val request =
                    "{\"username\":\"$email\", " +
                            "\"role\" : [\"ROLE_USER\"], " +
                            "\"password\":\"$password\", " +
                            "\"depId\" : \"$department\"}"
                val requestBody = request.toRequestBody("application/json".toMediaTypeOrNull())
                viewModel.signup(requestBody)
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 != null) {
            department = p2
            p0.getItemAtPosition(p2)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun validation(email:String, password:String, passwordR:String):Boolean{
        var check = true

        if(email.isEmpty()){
            binding.login.error = "Required"
            check = false
        }else if(!isValidEmail(email)){
            binding.login.error = "pollub.edu.pl domain required"
            check = false
        }
        if(password.isEmpty()){
            binding.password.error = "Required"
            check = false
        }else if(!isValidPassword(password)){
            binding.password.error ="Password :\n" +
                    "* at least 1 special character \n" +
                    "* no white spaces \n" +
                    "* at least 6 characters"
            check = false
        }
        if(passwordR.isEmpty()){
            binding.passwordRepeat.error = "Required"
            check = false
        }else if(password != passwordR){
            binding.passwordRepeat.error = "Different  Passwords"
            check = false
        }
        return check
    }

    private fun isValidEmail(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    private fun isValidPassword(str: String): Boolean{
        return PASSWORD_PATTERN.matcher(str).matches()
    }
}