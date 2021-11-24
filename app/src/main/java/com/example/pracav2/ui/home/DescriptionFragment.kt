package com.example.pracav2.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentDescriptionBinding
import com.example.pracav2.databinding.FragmentHomeBinding
import com.example.pracav2.ui.convertImg
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.startNewActivity
import com.example.pracav2.ui.visible
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private lateinit var binding: FragmentDescriptionBinding
    private val itemViewModel: ItemViewModel by activityViewModels()
    private val viewModel by viewModels<HomeViewModel>()

    private var idEvent : Int  = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDescriptionBinding.bind(view)



        itemViewModel.selectedItem.observe(viewLifecycleOwner, Observer { event ->
            binding.titleDetail.text =  event.name
            binding.descriptionDetail.text = event.description
            binding.imageView2.setImageBitmap(convertImg(event.imageData))
            binding.eventStartDate.text = event.data_start //TODO jak data nie null to odkomentować
            binding.eventEndDate.text = event.data_end //TODO jak data nie null to odkomentować
            binding.eventMaxMembers.text = event.max_number_of_contestant.toString()
            idEvent = event.id

            if(/*event.czyMoznaZapisac && */!event.czyZapisano){
                binding.signUpButton.visibility = View.VISIBLE
            }else {binding.signUpButton.visibility = View.GONE}
            Toast.makeText(requireContext(), event.czyMoznaZapisac.toString(), Toast.LENGTH_SHORT).show()
        })

        viewModel.enroll.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                }
                is Resource.Failure ->  {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show() }
            }
        })


        binding.signUpButton.setOnClickListener {
            //val body = "{\"eventId\":${args.userId} , \"userId\":$idEvent, \"roleInEvent\":\"contestant\"}"
            val body1 = "{\"eventId\": ${idEvent} , \"roleInEvent\":\"contestant\"}"
            Toast.makeText(requireContext(), body1, Toast.LENGTH_SHORT).show()
            val requestBody = body1.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.enroll(requestBody)
        }

    }


}