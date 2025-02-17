package com.example.andranfitmobile.data

data class Exercise(
    val Nombre: String,
    val Peso: Int,
    val Progresivo: Progressive,
    val Reps: Int,
    val Series: Int,
    val id: String
)

data class Progressive(
    val Tipo: String,
    val Variación: String
)