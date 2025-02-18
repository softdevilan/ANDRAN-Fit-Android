package com.example.andranfitmobile.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class User(
    val Activo: Boolean = true,
    val Entrenadores: Map<String, Entrenador> = emptyMap(),
    val FechaNacimiento: Long = 0,
    val Mediciones: Map<String, Medicion> = emptyMap(),
    val NivelActividad: Int = 0,
    val Nombre: Name = Name(),
    val Objetivos: Objetivos = Objetivos(),
    val Registro: Long = 0,
    val Sexo: String = "",
    val Trato: String = "",
    val Workouts: Workouts = Workouts()
) {
    @Exclude
    fun calcularEdad(): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = FechaNacimiento * 1000L
        val birthDate = calendar.time
        val today = Calendar.getInstance().time
        return today.year - birthDate.year - if (today.month < birthDate.month || (today.month == birthDate.month && today.date < birthDate.date)) 1 else 0
    }

    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): User {
            val activo = snapshot.child("Activo").getValue(Boolean::class.java) ?: false
            val entrenadores = snapshot.child("Entrenadores").children.map { Pair(it.key!!, Entrenador.fromSnapshot(it)) }.toMap()
            val fechaNacimiento = snapshot.child("FechaNacimiento").getValue(Long::class.java) ?: 0
            val mediciones = snapshot.child("Mediciones").children.map { Pair(it.key!!, Medicion.fromSnapshot(it)) }.toMap()
            val nivelActividad = snapshot.child("Nivel de actividad").getValue(Int::class.java) ?: 0
            val nombre = snapshot.child("Nombre").getValue(Name::class.java) ?: Name()
            val objetivos = snapshot.child("Objetivos").getValue(Objetivos::class.java) ?: Objetivos()
            val registro = snapshot.child("Registro").getValue(Long::class.java) ?: 0
            val sexo = snapshot.child("Sexo").getValue(String::class.java) ?: ""
            val trato = snapshot.child("Trato").getValue(String::class.java) ?: ""
            val workouts = Workouts.fromSnapshot(snapshot.child("Workouts"))
            return User(
                Activo = activo,
                Entrenadores = entrenadores,
                FechaNacimiento = fechaNacimiento,
                Mediciones = mediciones,
                NivelActividad = nivelActividad,
                Nombre = nombre,
                Objetivos = objetivos,
                Registro = registro,
                Sexo = sexo,
                Trato = trato,
                Workouts = workouts
            )
        }
    }
}

@IgnoreExtraProperties
data class Entrenador(
    val Desde: Long = 0
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Entrenador {
            val desde = snapshot.child("Desde").getValue(Long::class.java) ?: 0
            return Entrenador(
                Desde = desde
            )
        }
    }
}

@IgnoreExtraProperties
data class Medicion(
    val Altura: Int = 0,
    val Fecha: Long = 0,
    val Grasa: Int = 0,
    val Peso: Int = 0
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Medicion {
            val altura = snapshot.child("Altura").getValue(Int::class.java) ?: 0
            val fecha = snapshot.child("Fecha").getValue(Long::class.java) ?: 0
            val grasa = snapshot.child("Grasa").getValue(Int::class.java) ?: 0
            val peso = snapshot.child("Peso").getValue(Int::class.java) ?: 0
            return Medicion(
                Altura = altura,
                Fecha = fecha,
                Grasa = grasa,
                Peso = peso
            )
        }
    }
}

@IgnoreExtraProperties
data class Objetivos(
    val Deportivos: ObjetivosDeportivos = ObjetivosDeportivos(),
    val Físicos: ObjetivosFísicos? = null, // Cambiar a nullable
    val Nutricionales: ObjetivosNutricionales = ObjetivosNutricionales(),
    val Plan: String = ""
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Objetivos {
            val deportivos = snapshot.child("Deportivos").getValue(ObjetivosDeportivos::class.java) ?: ObjetivosDeportivos()
            val físicos = snapshot.child("Físicos").getValue(ObjetivosFísicos::class.java)
            val nutricionales = snapshot.child("Nutricionales").getValue(ObjetivosNutricionales::class.java) ?: ObjetivosNutricionales()
            val plan = snapshot.child("Plan").getValue(String::class.java) ?: ""
            return Objetivos(
                Deportivos = deportivos,
                Físicos = físicos,
                Nutricionales = nutricionales,
                Plan = plan
            )
        }
    }
}

@IgnoreExtraProperties
data class ObjetivosDeportivos(
    val EntrenamientosPorSemana: Int = 0
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): ObjetivosDeportivos {
            val entrenamientosPorSemana = snapshot.child("Entrenamientos por semana").getValue(Int::class.java) ?: 0
            return ObjetivosDeportivos(
                EntrenamientosPorSemana = entrenamientosPorSemana
            )
        }
    }
}

@IgnoreExtraProperties
data class ObjetivosFísicos(
    val Grasa: Int = 0,
    val Peso: Int = 0
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): ObjetivosFísicos {
            val grasa = snapshot.child("Grasa").getValue(Int::class.java) ?: 0
            val peso = snapshot.child("Peso").getValue(Int::class.java) ?: 0
            return ObjetivosFísicos(
                Grasa = grasa,
                Peso = peso
            )
        }
    }
}

@IgnoreExtraProperties
data class ObjetivosNutricionales(
    val Carbohidratos: Int = 0,
    val Grasas: Int = 0,
    val Kcal: Int = 0,
    val Proteínas: Int = 0,
    val Restricciones: Restriccion = Restriccion()
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): ObjetivosNutricionales {
            val carbohidratos = snapshot.child("Carbohidratos").getValue(Int::class.java) ?: 0
            val grasas = snapshot.child("Grasas").getValue(Int::class.java) ?: 0
            val kcal = snapshot.child("Kcal").getValue(Int::class.java) ?: 0
            val proteínas = snapshot.child("Proteínas").getValue(Int::class.java) ?: 0
            val restricciones = snapshot.child("Restricciones").getValue(Restriccion::class.java) ?: Restriccion()
            return ObjetivosNutricionales(
                Carbohidratos = carbohidratos,
                Grasas = grasas,
                Kcal = kcal,
                Proteínas = proteínas,
                Restricciones = restricciones
            )
        }
    }
}

@IgnoreExtraProperties
data class Restriccion(
    val restriccion: Map<String, String> = emptyMap()
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Restriccion {
            val restriccionesMap = mutableMapOf<String, String>()
            for (child in snapshot.children) {
                val key = child.key ?: continue
                val value = child.getValue(String::class.java) ?: continue
                restriccionesMap[key] = value
            }
            return Restriccion(
                restriccion = restriccionesMap
            )
        }
    }
}

@IgnoreExtraProperties
data class Workouts(
    val Completados: List<Workout> = emptyList(),
    val Pendientes: List<Workout> = emptyList(),
) {
    companion object {
        @JvmStatic
        fun fromSnapshot(snapshot: DataSnapshot): Workouts {
            val completados = snapshot.child("Completados").children.mapNotNull { it.getValue(Workout::class.java) }
            val pendientes = snapshot.child("Pendientes").children.mapNotNull { it.getValue(Workout::class.java) }
            return Workouts(
                Completados = completados,
                Pendientes = pendientes
            )
        }
    }
}