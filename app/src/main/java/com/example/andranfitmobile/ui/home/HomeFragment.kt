package com.example.andranfitmobile.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.MainActivity
import com.example.andranfitmobile.SharedViewModel
import com.example.andranfitmobile.databinding.FragmentHomeBinding
import com.example.andranfitmobile.ui.workouts.WorkoutAdapter

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var workoutAdapter: WorkoutAdapter
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: Iniciando HomeFragment")
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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
            Log.d(TAG, "ID recibido desde SharedViewModel: $userId")

            Log.d(TAG, "Llamando a cargarUsuario()")
            homeViewModel.cargarUsuario(userId)

            homeViewModel.cargarWorkouts(userId)

            // Si no hay workouts pendientes, mostrar noUpcomingWorkoutsText
            homeViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
                binding.noUpcomingWorkoutsText.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Limpiando binding")
    }

}