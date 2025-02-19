package com.example.andranfitmobile.ui.home

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

private const val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {

    private val _usuario = MutableLiveData<User>()
    val usuario: LiveData<User> get() = _usuario

    private val _workouts = MutableLiveData<List<Workout>>()
    val workouts: LiveData<List<Workout>> get() = _workouts

    fun cargarUsuario(userId: String) {
        Log.d(TAG, "cargarUsuarios: Iniciando carga de usuario desde Firebase en la ruta Usuarios/Clientes/$userId")
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes/$userId")

        database.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("Usuario obtenido:", dataSnapshot.value.toString())

                Log.d(TAG, "Creando usuario a partir de los datos recibidos de Firebase")
                val usuario = User.fromSnapshot(dataSnapshot)

                Log.d(TAG, "Usuario creado: $usuario")
                usuario?.let { _usuario.value = it }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled: Error al cargar usuario desde Firebase", databaseError.toException())
            }
        })
    }

    fun cargarWorkouts(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes").child(userId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = User.fromSnapshot(dataSnapshot)
                usuario?.let {
                    val allWorkouts = mutableListOf<Workout>()
                    allWorkouts.addAll(it.Workouts.Pendientes)
                    allWorkouts.sortByDescending { it.Fecha }

                    // Si hay workouts pendientes, mostrar el primero
                    if (allWorkouts.isNotEmpty()) {
                        val nextWorkout = mutableListOf<Workout>()
                        nextWorkout.add(allWorkouts[0])
                        _workouts.value = nextWorkout
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled: Error al cargar workouts desde Firebase", databaseError.toException())
            }
        })
    }


}