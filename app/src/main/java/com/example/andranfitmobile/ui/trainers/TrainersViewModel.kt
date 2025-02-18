package com.example.andranfitmobile.ui.trainers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.Trainer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TrainersViewModel : ViewModel() {

    private val _entrenadores = MutableLiveData<List<Trainer>>()
    val entrenadores: LiveData<List<Trainer>> get() = _entrenadores

    fun cargarEntrenadores() {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Entrenadores")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listaEntrenadores = mutableListOf<Trainer>()
                for (snapshot in dataSnapshot.children) {
                    val entrenador = Trainer.fromSnapshot(snapshot)
                    listaEntrenadores.add(entrenador)
                }
                _entrenadores.value = listaEntrenadores
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }
}