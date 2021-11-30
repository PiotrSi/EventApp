package com.example.pracav2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pracav2.databinding.FragmentAnalyzeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalyzeFragment : Fragment(R.layout.fragment_analyze) {

    private lateinit var binding: FragmentAnalyzeBinding
//    lateinit var barList: ArrayList<BarE>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnalyzeBinding.bind(view)



    }


}

