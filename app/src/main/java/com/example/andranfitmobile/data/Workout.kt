package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Workout(
    val Ejercicios: List<Exercise> = emptyList(),
    val Entrenador: String = "",
    val Fecha: Long = 0,
    val Tiempo: Int = 0,
    val id: String? = null
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Workout {
            val ejercicios = snapshot.child("Ejercicios").children.mapNotNull { it.getValue(Exercise::class.java) }
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

@IgnoreExtraProperties
data class Exercise(
    val Nombre: String = "",
    val Peso: Int = 0,
    val Progresivo: Progressive = Progressive(), // Se agregó un valor por defecto
    val Reps: Int = 0,
    val Series: Int = 0,
    val id: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Exercise {
            val nombre = snapshot.child("Nombre").getValue(String::class.java) ?: ""
            val peso = snapshot.child("Peso").getValue(Int::class.java) ?: 0
            val progresivo = snapshot.child("Progresivo").getValue(Progressive::class.java) ?: Progressive()
            val reps = snapshot.child("Reps").getValue(Int::class.java) ?: 0
            val series = snapshot.child("Series").getValue(Int::class.java) ?: 0
            val id = snapshot.child("id").getValue(String::class.java) ?: ""
            return Exercise(
                Nombre = nombre,
                Peso = peso,
                Progresivo = progresivo,
                Reps = reps,
                Series = series,
                id = id
            )
        }
    }
}

@IgnoreExtraProperties
data class Progressive(
    val Tipo: String = "",
    val Variación: String = ""
)
