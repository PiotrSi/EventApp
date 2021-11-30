package com.example.pracav2.ui.home

import android.icu.text.MessageFormat.format
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.pracav2.R
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.responses.EventResponseItem
import com.example.pracav2.databinding.FragmentDescriptionBinding
import com.example.pracav2.databinding.FragmentHomeBinding
import com.example.pracav2.ui.convertImg
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.startNewActivity
import com.example.pracav2.ui.visible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.internal.bind.util.ISO8601Utils.format
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity




@AndroidEntryPoint
class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private lateinit var binding: FragmentDescriptionBinding
    private val itemViewModel: ItemViewModel by activityViewModels()
    private val viewModel by viewModels<HomeViewModel>()

    private var idEvent : Int  = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDescriptionBinding.bind(view)


        val toolbar: Toolbar = binding.toolbar as Toolbar
        toolbar.setTitle("Title");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener {
//            fun onClick(view :View){
                Toast.makeText(requireContext(), "cofaj", Toast.LENGTH_SHORT).show()
//            }
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
//            Toast.makeText(requireContext(), event.czyMoznaZapisac.toString(), Toast.LENGTH_SHORT).show()



            if(event.czyMoznaOceniac )
            {
                binding.ratingBar.visibility = View.VISIBLE
                binding.rateButton.visibility = View.VISIBLE
            }else{
                binding.ratingBar.visibility = View.GONE
                binding.rateButton.visibility = View.GONE
            }
        })

        binding.rateButton.setOnClickListener {
            var rate : Float =binding.ratingBar.rating
            Toast.makeText(requireContext(), rate.toString(), Toast.LENGTH_SHORT).show()
        }

//        viewModel.enroll.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is Resource.Success -> {
//
//                }
//                is Resource.Failure ->  {
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show() }
//            }
//        })
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

        binding.signUpButton.setOnClickListener {
            //val body = "{\"eventId\":${args.userId} , \"userId\":$idEvent, \"roleInEvent\":\"contestant\"}"
            val body1 = "{\"eventId\": ${idEvent} , \"roleInEvent\":\"contestant\"}"
//            Toast.makeText(requireContext(), body1, Toast.LENGTH_SHORT).show()
            val requestBody = body1.toRequestBody("application/json".toMediaTypeOrNull())
            viewModel.enroll(requestBody)
        }

    }
    fun date(date :Date):String{
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR).toString()
        val month = cal.get(Calendar.MONTH).toString()
        val day = cal.get(Calendar.DAY_OF_MONTH).toString()
        val hour = cal.get(Calendar.HOUR_OF_DAY).toString()
        val min = cal.get(Calendar.MINUTE).toString()
        return "$hour:$min  $day/$month/$year"
    }


}