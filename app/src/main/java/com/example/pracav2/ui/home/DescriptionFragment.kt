package com.example.pracav2.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentDescriptionBinding
import com.example.pracav2.ui.convertImg
import com.example.pracav2.ui.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*


@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private lateinit var binding: FragmentDescriptionBinding
    private val itemViewModel: ItemViewModel by activityViewModels()
    private val viewModel by viewModels<HomeViewModel>()
    private val args : DescriptionFragmentArgs by navArgs()

    private var idEvent : Int  = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDescriptionBinding.bind(view)


        val toolbar: Toolbar = binding.toolbar
        when(args.from){
            "home" -> toolbar.title = "Event"
            "favorites" -> toolbar.title = "Your event"
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

        toolbar.setNavigationOnClickListener {
            when(args.from){
                "home" -> {val action = DescriptionFragmentDirections.actionDescriptionFragmentToHomeFragment2()
                            findNavController().navigate(action)}
                "favorites" -> {val action = DescriptionFragmentDirections.actionDescriptionFragmentToFavoritesFragment()
                    findNavController().navigate(action)}
            }
        }
        itemViewModel.selectedItem.observe(viewLifecycleOwner, Observer { event ->
            binding.titleDetail.text =  event.name
            binding.descriptionDetail.text = event.description
            if(event.imageData != null) {
                binding.imageView2.setImageBitmap(convertImg(event.imageData))
            }
            binding.eventStartDate.text = date(event.data_start)
            binding.eventEndDate.text = date(event.data_end)
            binding.eventMaxMembers.text = event.max_number_of_contestant.toString()
            idEvent = event.id



            if(event.czyMoznaZapisac && !event.czyZapisano){
                binding.signUpButton.visibility = View.VISIBLE
            }else {binding.signUpButton.visibility = View.GONE}




            if(event.czyMoznaOceniac && event.rate == 0f)
            {
                binding.ratingBar.visibility = View.VISIBLE
                binding.rateButton.visibility = View.VISIBLE
            }else if(event.rate > 0f){
                binding.yourRateLabel.visibility = View.VISIBLE
                binding.ratingBar.visibility = View.VISIBLE
                binding.ratingBar.rating = event.rate
                binding.ratingBar.setIsIndicator(true)
                binding.rateButton.visibility = View.GONE
            }
        })




        viewModel.enroll.observe(viewLifecycleOwner, Observer {enroll->
            when (enroll) {
                is Resource.Success -> {
//                    binding.progressbar.visible(false)
                    binding.signUpButton.visibility = View.GONE
                    Toast.makeText(requireContext(), enroll.value.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
//                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(enroll)
                }
            }
        })


        viewModel.rate.observe(viewLifecycleOwner, Observer {rate->
            when (rate) {
                is Resource.Success -> {
//                    binding.progressbar.visible(false)
                    binding.rateButton.visibility = View.GONE
                    Toast.makeText(requireContext(), rate.value.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
//                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(rate)
                }
            }
        })

        binding.signUpButton.setOnClickListener {
            //val body = "{\"eventId\":${args.userId} , \"userId\":$idEvent, \"roleInEvent\":\"contestant\"}"
            val body1 = "{\"eventId\": $idEvent , \"roleInEvent\":\"contestant\"}"
            val requestBody = body1.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.enroll(requestBody)
        }

        binding.rateButton.setOnClickListener {
            var rate : Float =binding.ratingBar.rating
            if(idEvent != -1 && rate != 0f) {
                binding.ratingBar.setIsIndicator(true)
                binding.yourRateLabel.visibility = View.VISIBLE
                binding.ratingBar.rating = rate
                val body = "{\"eventId\": $idEvent , \"rate\": $rate}"
                val requestBody = body.toRequestBody("application/json".toMediaTypeOrNull())
                viewModel.rate(requestBody)
            }else {
                Toast.makeText(requireContext(), "Rate can't be zero", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun date(date :Date):String{
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR).toString()
        val month = (cal.get(Calendar.MONTH)+1).toString()
        val day = cal.get(Calendar.DAY_OF_MONTH).toString()
//        val hour = cal.get(Calendar.HOUR_OF_DAY).toString()
        val hour = if(cal.get(Calendar.HOUR_OF_DAY) < 10) {
            "0${cal.get(Calendar.HOUR_OF_DAY)}"
        } else cal.get(Calendar.HOUR_OF_DAY).toString()
        val min = if(cal.get(Calendar.MINUTE) == 0) {
            "00"
        } else cal.get(Calendar.MINUTE).toString()

        return "$hour:$min  $day/$month/$year"
    }


}