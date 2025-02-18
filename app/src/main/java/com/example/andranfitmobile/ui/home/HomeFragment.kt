package com.example.andranfitmobile.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.MainActivity
import com.example.andranfitmobile.databinding.FragmentHomeBinding
import com.example.andranfitmobile.ui.workouts.WorkoutAdapter

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var workoutAdapter: WorkoutAdapter
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: Iniciando HomeFragment")

        arguments?.let {
            userId = it.getString("USER_ID")

            // Verificar si el ID de usuario es null
            if (userId == null) {
                userId = "ktbbOu0vGkNwxlt3JevyuYpUElW2"
                Log.d(TAG, "Null userId, inicializando a ktbbOu0vGkNwxlt3JevyuYpUElW2")
            } else {
                Log.d(TAG, "userId recibido: $userId")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar RecyclerView para mostrar los workouts
        val recyclerView: RecyclerView = binding.workoutRecyclerView
        workoutAdapter = WorkoutAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = workoutAdapter

        // Observar el usuario y los próximos workouts
        homeViewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            val trato = usuario.Trato
            val nombre = usuario.Nombre.Nombre
            val bienvenida = when (trato) {
                "M" -> "Bienvenido"
                "F" -> "Bienvenida"
                else -> "Hola"
            }
            Log.d(TAG, "Bindeando el texto de bienvenida con el nombre del usuario")
            binding.bienvenidaTextView.text = "$bienvenida, $nombre"
        }

        homeViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            workoutAdapter.submitList(workouts)
        }

        // Cargar el usuario y los próximos workouts
        Log.d(TAG, "Llamando a cargarUsuario()")
        userId?.let {
            Log.d(TAG, "Cargando usuario con ID: $it")
            homeViewModel.cargarUsuario(it)

            Log.d(TAG, "Cargando próximos workouts del usuario con ID: $it")
            homeViewModel.cargarWorkouts(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Limpiando binding")
    }

}