package com.example.andranfitmobile.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileViewModel : ViewModel() {

    private val _usuario = MutableLiveData<User>()
    val usuario: LiveData<User> get() = _usuario

    fun cargarUsuario(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("usuarios").child(userId)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = User.fromSnapshot(dataSnapshot)
                _usuario.value = usuario
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejo de errores
            }
        })
    }
}