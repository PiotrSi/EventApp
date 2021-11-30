package com.example.pracav2.ui.home

import android.os.Bundle
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val viewModel by viewModels<HomeViewModel>()

    private val viewModelItem: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView4) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

//        navController.addOnDestinationChangedListener{_,nd:NavDestination,_ ->
//            when(nd.id){
//               R.id.descriptionFragment -> binding.bottomNavigationView.visibility  = View.GONE
//                else -> binding.bottomNavigationView.visibility = View.VISIBLE
//            }
//        }

            viewModelItem.selectedItem.observe(this, Observer { item ->
//                Toast.makeText(this, "nie ma bara", Toast.LENGTH_SHORT).show()
//                binding.bottomNavigationView.visibility  = View.GONE
            })


    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        userPreferences.clear()
        startNewActivity(AuthActivity::class.java)
    }
}


//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // calling the action bar
//        var actionBar = getSupportActionBar()
//
//        // showing the back button in action bar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }
//    }
//
//    // this event will enable the back
//    // function to the button on press
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onContextItemSelected(item)
//    }
//}



//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // calling the action bar
//        var actionBar = getSupportActionBar()
//
//        if (actionBar != null) {
//
//            // Customize the back button
//            actionBar.setHomeAsUpIndicator(R.drawable.mybutton);
//
//            // showing the back button in action bar
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    // this event will enable the back
//    // function to the button on press
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onContextItemSelected(item)
//    }
//}