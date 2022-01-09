package com.example.pracav2.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pracav2.R
import com.example.pracav2.data.UserPreferences
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.responses.EventResponseItem
import com.example.pracav2.databinding.FragmentHomeBinding
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.logout
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), RecyclerViewClickListener{



    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val myAdapter by lazy {EventsViewAdapter(this@HomeFragment)}
    private val itemViewModel: ItemViewModel by activityViewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


            viewModel.getEvents()

        setupRecyclerview()

        viewModel.event.observe(viewLifecycleOwner, Observer { event ->
            when (event) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    var  myEvents :ArrayList<EventResponseItem> = arrayListOf()
                    event.value.forEach{
                        if(it.statusEvent == "ZAAKCEPTOWANY"){
                            myEvents.add(it)
                        }
                    }
                    if(myEvents.isEmpty()) {
                        binding.textView2.visibility = View.VISIBLE
                    }else{
                        binding.textView2.visibility = View.GONE
                        myAdapter.setData(myEvents)
                    }
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    binding.progressbar.visible(false)
                    event.isNetworkError
                    handleApiError(event)
                }
            }
        })




        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun setupRecyclerview(){
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }


    override fun onItemClick(view: View, event: EventResponseItem) {
        itemViewModel.selectItem(event)
        val action = HomeFragmentDirections.actionHomeFragment2ToDescriptionFragment("home")
        findNavController().navigate(action)
    }


}