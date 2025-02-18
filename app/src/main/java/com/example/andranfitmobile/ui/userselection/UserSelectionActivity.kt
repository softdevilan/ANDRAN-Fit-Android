package com.example.andranfitmobile.ui.userselection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.MainActivity
import com.example.andranfitmobile.data.User
import com.example.andranfitmobile.ui.userselection.adapter.UserAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val TAG = "UserSelectionActivity"

class UserSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val usuariosList = mutableListOf<Pair<String, User>>() // Almacenar ID y Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_user_selection)
        Log.d(TAG, "onCreate: Iniciando UserSelectionActivity")

        recyclerView = findViewById(R.id.recyclerView)
        userAdapter = UserAdapter(usuariosList) { userId ->
            navigateToMainActivity(userId)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        Log.d(TAG, "cargarUsuarios: Iniciando carga de usuarios desde Firebase")
        val database = FirebaseDatabase.getInstance().getReference("Usuarios/Clientes")

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d(TAG, "onDataChange: Datos recibidos desde Firebase")
                usuariosList.clear()

                for (snapshot in dataSnapshot.children) {
                    Log.d("FirebaseData", snapshot.value.toString())

                    val usuario = User.fromSnapshot(snapshot)
                    Log.d(TAG, "onDataChange: Usuario cargado: ${usuario.Nombre.Nombre} ${usuario.Nombre.Apellido1}")
                    usuariosList.add(Pair(snapshot.key!!, usuario))
                }

                userAdapter.notifyDataSetChanged()
                Log.d(TAG, "onDataChange: Lista de usuarios actualizada, tamaño: ${usuariosList.size}")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled: Error al cargar usuarios desde Firebase", databaseError.toException())
            }
        })
    }

    private fun navigateToMainActivity(userId: String) {

        Log.d(TAG, "navigateToMainActivity: Navegando a MainActivity con UserID: $userId")

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("USER_ID", userId)
        }

        startActivity(intent)
        finish()
    }
}