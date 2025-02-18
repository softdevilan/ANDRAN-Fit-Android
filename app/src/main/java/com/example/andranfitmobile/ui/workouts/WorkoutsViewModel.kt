package com.example.andranfitmobile.ui.workouts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.User
import com.example.andranfitmobile.data.Workout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkoutsViewModel : ViewModel() {

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    fun cargarWorkouts(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes").child(userId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(User::class.java)
                usuario?.let {
                    val allWorkouts = mutableListOf<Workout>()
                    allWorkouts.addAll(it.Workouts.Completados)
                    allWorkouts.addAll(it.Workouts.Pendientes)
                    allWorkouts.sortBy { it.Fecha }
                    _workouts.value = allWorkouts
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }
}