package com.example.andranfitmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.andranfitmobile.databinding.ActivityMainBinding
import com.example.andranfitmobile.ui.home.HomeFragment
import com.example.andranfitmobile.ui.workouts.WorkoutsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del usuario pasado desde UserSelectionActivity
        userId = intent.getStringExtra("USER_ID")

        val navView: com.google.android.material.bottomnavigation.BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_workouts, R.id.navigation_profile, R.id.navigation_trainers
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Pasar el ID del usuario a los fragments
        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.navigation_home -> {
                    val bundle = Bundle().apply {
                        putString("USER_ID", userId)
                    }
                    navController.setGraph(R.navigation.mobile_navigation, bundle)
                }
                R.id.navigation_workouts -> {
                    val bundle = Bundle().apply {
                        putString("USER_ID", userId)
                    }
                    navController.setGraph(R.navigation.mobile_navigation, bundle)
                }
                R.id.navigation_profile -> {
                    val bundle = Bundle().apply {
                        putString("USER_ID", userId)
                    }
                    navController.setGraph(R.navigation.mobile_navigation, bundle)
                }
                R.id.navigation_trainers -> {
                    val bundle = Bundle().apply {
                        putString("USER_ID", userId)
                    }
                    navController.setGraph(R.navigation.mobile_navigation, bundle)
                }
            }
        }
    }
}