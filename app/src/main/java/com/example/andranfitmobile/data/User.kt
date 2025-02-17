package com.example.andranfitmobile.data

import java.util.*

data class User(
    val Activo: Boolean,
    val Login: Login,
    val Entrenadores: Map<String, Entrenador>,
    val FechaNacimiento: Long,
    val Mediciones: Map<String, Medicion>,
    val NivelActividad: Int,
    val Nombre: Name,
    val Objetivos: Objetivos,
    val Registro: Long,
    val Sexo: String,
    val Trato: String,
    val Workouts: Workouts
) {
    fun calcularEdad(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = FechaNacimiento * 1000L
        val birthDate = calendar.time
        val today = Calendar.getInstance().time
        return today.year - birthDate.year - if (today.month < birthDate.month || (today.month == birthDate.month && today.date < birthDate.date)) 1 else 0
    }
}

data class Entrenador(
    val Desde: Long
)

data class Medicion(
    val Grasa: Int,
    val Peso: Int,
    val Fecha: Long
)

data class MedicionActual(
    val Altura: Int,
    val Fecha: Long,
    val Grasa: Int,
    val Peso: Int
)

data class Objetivos(
    val Deportivos: ObjetivosDeportivos,
    val Físicos: ObjetivosFisicos,
    val Nutricionales: ObjetivosNutricionales,
    val Plan: String
)

data class ObjetivosDeportivos(
    val EntrenamientosPorSemana: Int
)

data class ObjetivosFisicos(
    val Grasa: Int,
    val Peso: Int
)

data class ObjetivosNutricionales(
    val Carbohidratos: Int,
    val Grasas: Int,
    val Kcal: Int,
    val Proteínas: Int,
    val Restricciones: RestriccionesNutricionales
)

data class RestriccionesNutricionales(
    val `Gluten-free`: String,
    val `Low-carb`: String
)

data class Workouts(
    val Completados: List<Workout>,
    val Pendientes: Map<String, Workout>
)