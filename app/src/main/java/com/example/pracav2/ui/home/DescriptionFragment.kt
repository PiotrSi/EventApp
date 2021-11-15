package com.example.pracav2.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.pracav2.R
import com.example.pracav2.databinding.FragmentDescriptionBinding
import com.example.pracav2.databinding.FragmentHomeBinding
import com.example.pracav2.ui.convertImg
import com.google.android.material.bottomnavigation.BottomNavigationView


class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private lateinit var binding: FragmentDescriptionBinding
    private val itemViewModel: ItemViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDescriptionBinding.bind(view)



        itemViewModel.selectedItem.observe(viewLifecycleOwner, Observer { event ->
            binding.titleDetail.text =  event.name
            binding.descriptionDetail.text = event.description
            binding.imageView2.setImageBitmap(convertImg(event.imageData))
//            binding.eventStartDate.text = event.data_start.toString() TODO jak data nie null to odkomentować
//            binding.eventStartDate.text = event.data_end.toString() TODO jak data nie null to odkomentować
            binding.eventMaxMembers.text = event.max_number_of_contestant.toString()
        })


    }


}