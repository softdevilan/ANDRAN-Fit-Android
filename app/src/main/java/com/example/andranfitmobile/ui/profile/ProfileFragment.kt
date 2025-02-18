package com.example.andranfitmobile.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.andranfitmobile.R
import com.example.andranfitmobile.SharedViewModel
import com.example.andranfitmobile.data.User
import com.example.andranfitmobile.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: Iniciando ProfileFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observar el usuario
        profileViewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            Log.d(TAG, "onCreateView: Usuario cargado: ${usuario.Nombre.Nombre} ${usuario.Nombre.Apellido1}")

            val nombreCompleto = "${usuario.Nombre.Nombre} ${usuario.Nombre.Apellido1}"
            val fechaNacimientoFormateada = formatDate(usuario.FechaNacimiento)
            val altura = usuario.Mediciones["Actual"]?.Altura ?: 0
            val peso = usuario.Mediciones["Actual"]?.Peso ?: 0
            val grasa = usuario.Mediciones["Actual"]?.Grasa ?: 0
            val medicionFechaFormateada = formatDate(usuario.Mediciones["Actual"]?.Fecha ?: 0)

            binding.nombreTextView.text = nombreCompleto
            binding.fechaNacimientoTextView.text = "Fecha de Nacimiento: $fechaNacimientoFormateada"
            binding.alturaTextView.text = "Altura: $altura cm"
            binding.pesoTextView.text = "Peso: $peso kg"
            binding.grasaTextView.text = "Grasa: $grasa %"
            binding.medicionFechaTextView.text = "Fecha de Medición: $medicionFechaFormateada"

            // Mostrar objetivos deportivos
            val deportivos = usuario.Objetivos.Deportivos.EntrenamientosPorSemana
            binding.deportivosTextView.text = "Entrenamientos por Semana: $deportivos"

            // Mostrar objetivos físicos
            val fisicos = usuario.Objetivos.Físicos
            if (fisicos != null) {
                binding.fisicosTextView.text = "Objetivos Físicos:\n" +
                        "Peso: ${fisicos.Peso} kg\n" +
                        "Grasa: ${fisicos.Grasa} %"
            } else {
                binding.fisicosTextView.text = "No hay objetivos físicos registrados"
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
            Log.d(TAG, "ID recibido desde SharedViewModel: $userId")
            Log.d(TAG, "Llamando a cargarUsuario()")

            profileViewModel.cargarUsuario(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Destruyendo ProfileFragment")
    }

    private fun formatDate(timestamp: Long): String {
        if (timestamp == 0L) return ""
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp * 1000))
    }
}