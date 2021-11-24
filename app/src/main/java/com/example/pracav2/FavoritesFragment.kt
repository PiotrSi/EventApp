package com.example.pracav2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pracav2.R
import com.example.pracav2.data.UserPreferences
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.responses.EventResponseItem
import com.example.pracav2.databinding.FragmentFavoritesBinding
import com.example.pracav2.databinding.FragmentHomeBinding
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.*
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), RecyclerViewClickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val myAdapter by lazy { EventsViewAdapter(this@FavoritesFragment) }
    private val itemViewModel: ItemViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)

        val userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.refreshToken.first() }
        if (token != null) {
            viewModel.getEvents(token)
        }

        setapRecyclerview()

        viewModel.event.observe(viewLifecycleOwner, Observer {event->
            when (event) {
                is Resource.Success -> {
//                    binding.progressbar.visible(false)
//                     lateinit var  myEvents :ArrayList<EventResponseItem>
                    event.value.forEach{
                        if(it.czyZapisano){
//                            myEvents.add(it)
                        }else {it.id}
                    }
//                    if(myEvents!=null) {
//                        myAdapter.setData(myEvents)
//                    }
                }
                is Resource.Loading -> {
//                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(event)
                }
            }
        })
    }

    private fun setapRecyclerview(){
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    override fun onItemClick(view: View, event: EventResponseItem) {
        Toast.makeText(requireContext(), "kliknięto obrazek "+event.name, Toast.LENGTH_SHORT).show()
        itemViewModel.selectItem(event)
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDescriptionFragment()
        findNavController().navigate(action)
    }
}