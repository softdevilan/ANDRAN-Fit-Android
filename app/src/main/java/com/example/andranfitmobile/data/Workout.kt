package com.example.andranfitmobile.data

data class Workout(
    val Ejercicios: List<Exercise>,
    val Entrenador: String,
    val Fecha: Long,
    val Tiempo: Int,
    val id: String? = null
)

