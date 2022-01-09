package com.example.pracav2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pracav2.data.network.Resource
import com.example.pracav2.data.responses.EventResponseItem
import com.example.pracav2.databinding.FragmentFavoritesBinding
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.*
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites), RecyclerViewClickListener {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val myAdapter by lazy { EventsViewAdapter(this@FavoritesFragment) }
    private val itemViewModel: ItemViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)


        viewModel.getEvents()

        setupRecyclerview()

        viewModel.event.observe(viewLifecycleOwner, { event->
            when (event) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)

                    var  myEvents :ArrayList<EventResponseItem> = arrayListOf()
                    event.value.forEach{
                        if(it.czyZapisano){
                            myEvents.add(it)
                        }//else {it.id}
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
                    handleApiError(event)
                }
            }
        })
    }

    private fun setupRecyclerview(){
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }
    override fun onItemClick(view: View, event: EventResponseItem) {
        itemViewModel.selectItem(event)
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDescriptionFragment("favorites")
        findNavController().navigate(action)
    }
}