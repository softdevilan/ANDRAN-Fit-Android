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
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
            Log.d(TAG, "ID recibido desde SharedViewModel: $userId")

            // Configurar RecyclerView para mostrar los workouts
            val recyclerView: RecyclerView = binding.workoutRecyclerView
            workoutAdapter = WorkoutAdapter(false, userId)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = workoutAdapter

            // Configurar RecyclerView para mostrar los workouts pasados
            val workoutsRecyclerView: RecyclerView = binding.workoutRecyclerView
            workoutAdapter = WorkoutAdapter(true, userId)
            workoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            workoutsRecyclerView.adapter = workoutAdapter

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

            Log.d(TAG, "Antes de observar homeViewModel.workouts")
            homeViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
                Log.d(TAG, "workouts observados: ${workouts.size}")
                workoutAdapter.submitList(workouts)

                binding.noUpcomingWorkoutsText.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
                Log.d(TAG, "Mostrando noUpcomingWorkoutsText")
            }

            Log.d(TAG, "Llamando a cargarUsuario()")
            homeViewModel.cargarUsuario(userId)

            Log.d(TAG, "Llamando a cargarWorkouts()")
            homeViewModel.cargarWorkouts(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Limpiando binding")
    }

}