package com.example.andranfitmobile.data

data class Trainer(
    val Clientes: Map<String, Cliente>,
    val Login: Login,
    val Nombre: Name,
    val Trato: String
)
data class Cliente(
    val Desde: Long
)
