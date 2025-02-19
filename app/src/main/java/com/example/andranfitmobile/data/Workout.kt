package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Workout(
    val ejercicios: List<Exercise> = emptyList(),
    val entrenador: String = "",
    val fecha: Long = 0,
    val tiempo: Int = 0,
    val id: String? = null
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Workout {
            val ejercicios = snapshot.child("ejercicios").children.mapNotNull { it.getValue(Exercise::class.java) }
            val entrenador = snapshot.child("entrenador").getValue(String::class.java) ?: ""
            val fecha = snapshot.child("fecha").getValue(Long::class.java) ?: 0
            val tiempo = snapshot.child("tiempo").getValue(Int::class.java) ?: 0
            val id = snapshot.child("id").getValue(String::class.java)
            return Workout(
                ejercicios = ejercicios,
                entrenador = entrenador,
                fecha = fecha,
                tiempo = tiempo,
                id = id
            )
        }
    }
}

@IgnoreExtraProperties
data class Exercise(
    val nombre: String = "",
    val peso: Int = 0,
    val progresivo: Progressive = Progressive(), // Se agregó un valor por defecto
    val reps: Int = 0,
    val series: Int = 0,
    val id: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Exercise {
            val nombre = snapshot.child("nombre").getValue(String::class.java) ?: ""
            val peso = snapshot.child("peso").getValue(Int::class.java) ?: 0
            val progresivo = snapshot.child("progresivo").getValue(Progressive::class.java) ?: Progressive()
            val reps = snapshot.child("reps").getValue(Int::class.java) ?: 0
            val series = snapshot.child("series").getValue(Int::class.java) ?: 0
            val id = snapshot.child("id").getValue(String::class.java) ?: ""
            return Exercise(
                nombre = nombre,
                peso = peso,
                progresivo = progresivo,
                reps = reps,
                series = series,
                id = id
            )
        }
    }
}

@IgnoreExtraProperties
data class Progressive(
    val tipo: String = "",
    val variación: String = ""
)
