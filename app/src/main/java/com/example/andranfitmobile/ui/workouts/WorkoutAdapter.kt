package com.example.andranfitmobile.ui.workouts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.data.Workout
import com.example.andranfitmobile.data.Exercise
import java.text.SimpleDateFormat
import java.util.*

class WorkoutAdapter : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private var workouts: List<Workout> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    override fun getItemCount(): Int = workouts.size

    fun submitList(newWorkouts: List<Workout>) {
        workouts = newWorkouts
        notifyDataSetChanged()
    }

    inner class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fechaTextView: TextView = itemView.findViewById(R.id.fechaTextView)
        private val ejerciciosTextView: TextView = itemView.findViewById(R.id.ejerciciosTextView)

        fun bind(workout: Workout) {
            val fechaFormateada = formatDate(workout.Fecha)
            fechaTextView.text = fechaFormateada

            val ejercicios = workout.Ejercicios.joinToString(", ") { ejercicio ->
                "${ejercicio.Nombre} (${ejercicio.Series}x${ejercicio.Reps})"
            }
            ejerciciosTextView.text = ejercicios
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            return sdf.format(Date(timestamp * 1000))
        }
    }
}