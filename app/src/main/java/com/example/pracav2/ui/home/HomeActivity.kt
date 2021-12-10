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

        navController.addOnDestinationChangedListener{_,nd:NavDestination,_ ->
            when(nd.id){
               R.id.descriptionFragment -> binding.bottomNavigationView.visibility  = View.GONE
               R.id.analyzeFragment ->  binding.bottomNavigationView.visibility  = View.GONE
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

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

//slajd 1
//dzień dobry,
//realizowanym imie naziwko promotorem jest Dr inż Maria
//slajd 2
//podczas tej prezentacji omowiemy cel pracy, dokonamy
//slajd 3
//Celm pracy było zaprojektowanie i zaimplementowanie systemu informatycznego składającego sie z aplikacji internetowej i mobilnej, system umożliwia korzystanie użytkoiwkom takim jak
//-student
//-pracwonik firmy zenętrzej
//-pracownik politechnikowy
//-administrator
//aplikacja internetowa przeznaczona jest dla wszystkich użytkoników natomiast mobilna tylko dla studentów
//
//slajd 4
//podjeliśmy się tematu tej pracy ponież przeglądając rynek nie znaleźliśmy systemu
//który jest skierowany dla uczelni  i jednnocześnie składa się z aplikacji internetowej i mobilnej
//
//strona internetowa uniwersytetu warszwskiego umożliwia tworzenie wydarzenie ale bez możliwości przeglądania i  zapisania się przez studentów na dane wydarzenie  jak i ocenianie danego wydarzenia
//
//aplikacje mobilne dostępne na rynku nie są przeznaczone dla uczelni i utrudniają tworzenie wydarzenia na małym ekranie telefonu, nasz system będzie wygodny dla użytkownika ponieważ wypłenianie formularzy będzie się odbywać w aplikacji internetowej
//
//slajd 5
//na potrzeby naszej pracy wykorzystaliśmy następujące technologie:
//spring boot - jest to szkielet progrgamistyczny javy któy u łatwia tworzenie aplikaji internetowych 		dzięki wbudowanemu serverowi //oraz dostarzczeniu podstawowych zapytań do bazy danych
//mySQL - relacyjna baza danych oparta na strukturze zapytań sql wybraliśmy ja ze względu że jest darmowa i popularna
//Angular - szkielet programistyczny języka typy scryt, służący do tworzenia skalowalnych aplikacji internetowych opierający się na komponentach
//
//Retrofit -biblioteka służąca do bezpiecznej komunikiacji między aplikacją mobilną a serverem, umożliwiająca wymianę danych w formie schematu json
//
//android studio - jest to środowisko programistyczne umśliwiające programowanie aplikacji mobilnych na system android, stworzone i wspierane przez googla
//
//kotlin - język programowania opraty na javie, umożliwiający pisanie aplikacji na różnych platformach, na rzecz projektu wykorzystany do napisania aplikiacji mobilnej
//
//slajd 6
//w ramach pracy iinzynierskiej okręsliliśmy wymagania funk i nie funk
//stworzyliśmy
//
//na zamieszczonym obraku został przedstawiony diagram erd naszego systemu
//
//slajd 7
//aplikacja internetowa umożliwia
//rejestracje i logowanie
//podgląd profilu użytnownika
//tworzenie wydarzenia przez formularz co jest widoczne na załaczonym obrazku
//
//slajd 8
//jak również
//przeglądanie wydarzeń
//oraz szczegóły danego wydarzenia
//możliwośc zapisania się oraz oceny wydarzenia
//slajd 9
//aplikacja mobilna stworzona dla studentów umożliwia
//rejestracje logowanie
//daje mozliwość przeglądania wydarzeń
//zapisanie się i ocenienie konkretnego wydarznia
//oraz statysykę uczestniczenia na wydarzeniach z podziałem na miesiące
//slajd 10
//aplikacje zostały przetestowane i nie wykazały żadnych błędów , cel pracy został osiągnięty
//dziękujmey za uwagę

















