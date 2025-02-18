package com.example.andranfitmobile.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.andranfitmobile.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val TAG = "ProfileViewModel"

class ProfileViewModel : ViewModel() {

    private val _usuario = MutableLiveData<User>()
    val usuario: LiveData<User> get() = _usuario

    fun cargarUsuario(userId: String) {
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes").child(userId)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = User.fromSnapshot(dataSnapshot)
                _usuario.value = usuario
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled: Error al cargar usuario desde Firebase", databaseError.toException())
            }
        })
    }
}