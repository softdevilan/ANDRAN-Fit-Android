package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot

data class Trainer(
    val Clientes: Map<String, Cliente> = emptyMap(),
    val Login: Login = Login(),
    val Nombre: Name = Name(),
    val Trato: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Trainer {
            val clientes = snapshot.child("Clientes").children.map { Pair(it.key!!, Cliente.fromSnapshot(it)) }.toMap()
            val login = snapshot.child("Login").getValue(Login::class.java) ?: Login()
            val nombre = snapshot.child("Nombre").getValue(Name::class.java) ?: Name()
            val trato = snapshot.child("Trato").getValue(String::class.java) ?: ""
            return Trainer(
                Clientes = clientes,
                Login = login,
                Nombre = nombre,
                Trato = trato
            )
        }
    }
}

data class Cliente(
    val Desde: Long = 0
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Cliente {
            val desde = snapshot.child("Desde").getValue(Long::class.java) ?: 0
            return Cliente(
                Desde = desde
            )
        }
    }
}