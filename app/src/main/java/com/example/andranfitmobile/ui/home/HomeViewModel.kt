package com.example.andranfitmobile.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.User
import com.example.andranfitmobile.data.Workout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    private val _usuario = MutableLiveData<User>()
    val usuario: LiveData<User> get() = _usuario

    private val _proximosWorkouts = MutableLiveData<List<Workout>>()
    val proximosWorkouts: LiveData<List<Workout>> get() = _proximosWorkouts

    fun cargarUsuario(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes").child(userId)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(User::class.java)
                usuario?.let { _usuario.value = it }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }


}