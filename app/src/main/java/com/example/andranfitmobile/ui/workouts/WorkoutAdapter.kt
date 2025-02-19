package com.example.andranfitmobile.ui.workouts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.data.Workout
import com.example.andranfitmobile.data.Exercise
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter(private val isCompletedList: Boolean, private val clienteId: String) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private var workouts: List<Workout> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout, isCompletedList)
    }

    override fun getItemCount(): Int = workouts.size

    fun submitList(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        private val ejerciciosTextView: TextView = itemView.findViewById(R.id.ejerciciosTextView)
        private val completarButton: Button = itemView.findViewById(R.id.completarButton)

        fun bind(workout: Workout, isCompleted: Boolean) {
            val fechaFormateada = formatDate(workout.fecha)
            fechaTextView.text = fechaFormateada

            val ejercicios = workout.ejercicios.joinToString(", ") { ejercicio: Exercise ->
                "${ejercicio.nombre} (${ejercicio.series}x${ejercicio.reps})"
            }
            ejerciciosTextView.text = ejercicios

            // Mostrar botón solo si el workout no está completado
            completarButton.visibility = if (isCompleted) View.GONE else View.VISIBLE
            completarButton.setOnClickListener {
                marcarComoCompletado(workout)
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }
    }

    private fun marcarComoCompletado(workout: Workout) {
        val database = FirebaseDatabase.getInstance().reference

        Log.d("marcarComoCompletado", "Datos del workout: ${workout}")
        val completadosRef =
            workout.id?.let {
                database.child("Usuarios").child("Clientes").child(clienteId).child("Workouts").child("Completados").child(
                    it
                )
            }

        completadosRef?.setValue(workout)?.addOnSuccessListener {
            workout.id.let {
                database.child("Usuarios").child("Clientes").child(clienteId).child("Workouts")
                    .child("Pendientes").child(
                        it
                    )
            }.removeValue()
        }
    }
}
