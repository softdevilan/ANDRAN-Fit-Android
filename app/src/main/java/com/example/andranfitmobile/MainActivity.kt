package com.example.andranfitmobile

import android.content.Intent
import androidx.activity.viewModels
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.andranfitmobile.databinding.ActivityMainBinding
import com.example.andranfitmobile.ui.userselection.UserSelectionActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el ID del usuario pasado desde UserSelectionActivity
        userId = intent.getStringExtra("USER_ID")
        Log.d(TAG, "onCreate: UserID obtenido = ${userId}")

        // Si no se ha recibido el ID del usuario, navegar a UserSelectionActivity
        if (userId == null) {
            Log.d(TAG, "onCreate: No se recibió UserID, navegando a UserSelectionActivity")
            val intent = Intent(this, UserSelectionActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        sharedViewModel.setUserId(userId!!)

        val navView: com.google.android.material.bottomnavigation.BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_workouts, R.id.navigation_profile, R.id.navigation_trainers
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Pasar el ID del usuario a los fragments
        // !! AYUDA !! El error que tengo es que a la hora de recibir el ID del usuario desde los Fragments, no lo recibe
        // El ID lo recibe desde los fragments usando:
        // arguments?.let { userId = it.getString("USER_ID") }
        // userID es null después de esto, así que no lo está recibiendo, pero tiene valor en el momento de pasarlo desde la MainActivity
        navController.addOnDestinationChangedListener { _, destination, _ ->
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