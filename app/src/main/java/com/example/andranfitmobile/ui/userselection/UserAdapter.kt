package com.example.andranfitmobile.ui.userselection.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.data.User

class UserAdapter(
    private val usuarios: List<Pair<String, User>>,
    private val onUserClick: (String) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val (userId, usuario) = usuarios[position]
        holder.bind(usuario)
        holder.itemView.setOnClickListener {
            onUserClick(userId)
        }
    }

    override fun getItemCount(): Int = usuarios.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreTextView)

        fun bind(usuario: User) {
            nombreTextView.text = buildString {
                append(usuario.Nombre.Nombre)
                append(" ")
                append(usuario.Nombre.Apellido1)
            }
        }
    }
}