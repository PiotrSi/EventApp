package com.example.pracav2.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pracav2.FavoritesFragment
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

    private var idUser: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)



        viewModel.getUser()
        val userPreferences = UserPreferences(requireContext())
        val token = runBlocking { userPreferences.accessToken.first() }
        if (token != null) {
            viewModel.getEvents("Bearer $token")
        }

        setapRecyclerview()

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    myAdapter.setData(it.value)
                    it.value.forEach{
                        if(it.czyZapisano){
//                            myEvents.add(it)
                        }else {it.id}
                    }
//                    binding.recyclerView.apply{
//                        setHasFixedSize(true)
//                        layoutManager = LinearLayoutManager(activity)
//                        binding.recyclerView.adapter = EventsViewAdapter(it.value, this@HomeFragment)
//                    }
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }

//            viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
//                recycler_view_movies.also {
//                    it.layoutManager = LinearLayoutManager(requireContext())
//                    it.setHasFixedSize(true)
//                    it.adapter = MoviesAdapter(movies, this)
//                }
//            })
//            binding.recyclerView.apply {
//                setHasFixedSize(true)
//                layoutManager = LinearLayoutManager(this@MainActivity)
//                ConstraintLayoutManager
//            }
        })






//        viewModel.user.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is Resource.Success -> {
//                    Toast.makeText(requireContext(), it.value.message, Toast.LENGTH_SHORT).show()
//                }
//                is Resource.Loading -> {
////                    binding.progressbar.visible(true)
//                }
//                is Resource.Failure -> {   //TODO TUTAJ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
////                    handleApiError(it)
//                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
//                }
//            }
//        })
            idUser = 3



        binding.buttonLogout.setOnClickListener {
            //val action = HomeFragmentDirections.actionHomeFragment2ToDescriptionFragment()
            //findNavController().navigate(action)
            //view.findNavController().navigate(R.id.action_homeFragment_to_descriptionFragment)
            logout()
        }
    }

    private fun setapRecyclerview(){
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }


    override fun onItemClick(view: View, event: EventResponseItem) {
//        Toast.makeText(requireContext(), "kliknięto obrazek "+event.name, Toast.LENGTH_SHORT).show()
        itemViewModel.selectItem(event)
        val action = HomeFragmentDirections.actionHomeFragment2ToDescriptionFragment()
        findNavController().navigate(action)
    }

//    private fun updateUI(user: User) {
//        with(binding) {
//            textViewId.text = user.id.toString()
//            textViewName.text = user.name
//            textViewEmail.text = user.email
//        }
//    }
}