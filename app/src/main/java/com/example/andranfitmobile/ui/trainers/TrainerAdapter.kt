package com.example.andranfitmobile.ui.trainers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.data.Trainer

class TrainerAdapter : RecyclerView.Adapter<TrainerAdapter.TrainerViewHolder>() {

    private var entrenadores: List<Trainer> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trainer, parent, false)
        return TrainerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainerViewHolder, position: Int) {
        val entrenador = entrenadores[position]
        holder.bind(entrenador)
    }

    override fun getItemCount(): Int = entrenadores.size

    fun submitList(newEntrenadores: List<Trainer>) {
        entrenadores = newEntrenadores
        notifyDataSetChanged()
    }

    inner class TrainerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)

        fun bind(entrenador: Trainer) {
            val nombreCompleto = "${entrenador.Nombre.Nombre} ${entrenador.Nombre.Apellido1}"
            nombreTextView.text = nombreCompleto

            val email = "e-mail: " + (entrenador.Contacto["email"] ?: "No disponible")
            val phone = "Teléfono: " + (entrenador.Contacto["phone"] ?: "No disponible")
            emailTextView.text = email
        }
    }
}