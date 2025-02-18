package com.example.andranfitmobile.ui.workouts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.User
import com.example.andranfitmobile.data.Workout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val TAG = "WorkoutsViewModel"

class WorkoutsViewModel : ViewModel() {

    private val _workouts = MutableLiveData<List<Workout>>()
    private val _incomingWorkouts = MutableLiveData<List<Workout>>()
    private val _completedWorkouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    fun cargarWorkouts(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes").child(userId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = User.fromSnapshot(dataSnapshot)
                usuario?.let {
                    val allWorkouts = mutableListOf<Workout>()
                    val incomingWorkouts = mutableListOf<Workout>()
                    val completedWorkouts = mutableListOf<Workout>()

                    incomingWorkouts.addAll(it.Workouts.Pendientes)
                    completedWorkouts.addAll(it.Workouts.Completados)

                    allWorkouts.addAll(incomingWorkouts)
                    allWorkouts.addAll(completedWorkouts)

                    allWorkouts.sortBy { it.Fecha }
                    incomingWorkouts.sortBy { it.Fecha }
                    completedWorkouts.sortBy { it.Fecha }

                    _incomingWorkouts.value = incomingWorkouts
                    _completedWorkouts.value = completedWorkouts
                    _workouts.value = allWorkouts
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled: Error al cargar workouts desde Firebase", databaseError.toException())
            }
        })
    }
}