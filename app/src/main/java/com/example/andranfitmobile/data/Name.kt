package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Name(
    val Apellido1: String = "",
    val Apellido2: String = "",
    val Nombre: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Name {
            val apellido1 = snapshot.child("Apellido1").getValue(String::class.java) ?: ""
            val apellido2 = snapshot.child("Apellido2").getValue(String::class.java) ?: ""
            val nombre = snapshot.child("Nombre").getValue(String::class.java) ?: ""
            return Name(
                Apellido1 = apellido1,
                Apellido2 = apellido2,
                Nombre = nombre
            )
        }
    }
}
