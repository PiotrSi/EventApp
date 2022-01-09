package com.example.pracav2.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.pracav2.R
import com.example.pracav2.data.UserPreferences
import com.example.pracav2.databinding.ActivityHomeBinding
import com.example.pracav2.ui.auth.AuthActivity
import com.example.pracav2.ui.startNewActivity
import com.example.pracav2.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import android.app.Application
import com.google.android.material.internal.ContextUtils.getActivity


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel by viewModels<HomeViewModel>()

//    private val viewModelItem: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences(this)
        val token: String? = runBlocking { userPreferences.accessToken.first() }
        if (token != null) {
            Log.d("TOKEN1", token)
        }


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView4) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_,nd:NavDestination,_ ->
            when(nd.id){
               R.id.descriptionFragment -> binding.bottomNavigationView.visibility  = View.GONE
               R.id.analyzeFragment ->  binding.bottomNavigationView.visibility  = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

//            viewModelItem.selectedItem.observe(this, Observer { item ->
////                Toast.makeText(this, "nie ma bara", Toast.LENGTH_SHORT).show()
////                binding.bottomNavigationView.visibility  = View.GONE
//            })


    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
        val name :String = userPreferences.accessToken.toString()
        Log.d("TOKENPOLOGOUT",name)
    }
}