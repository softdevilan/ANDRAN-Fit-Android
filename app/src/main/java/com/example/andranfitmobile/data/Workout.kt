package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Workout(
    val Ejercicios: List<Ejercicio> = emptyList(),
    val Entrenador: String = "",
    val Fecha: Long = 0,
    val Tiempo: Int = 0,
    val id: String? = null
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Workout {
            val ejercicios = snapshot.child("Ejercicios").children.map { Ejercicio.fromSnapshot(it) }.toList()
            val entrenador = snapshot.child("Entrenador").getValue(String::class.java) ?: ""
            val fecha = snapshot.child("Fecha").getValue(Long::class.java) ?: 0
            val tiempo = snapshot.child("Tiempo").getValue(Int::class.java) ?: 0
            val id = snapshot.child("id").getValue(String::class.java)
            return Workout(
                Ejercicios = ejercicios,
                Entrenador = entrenador,
                Fecha = fecha,
                Tiempo = tiempo,
                id = id
            )
        }
    }
}

