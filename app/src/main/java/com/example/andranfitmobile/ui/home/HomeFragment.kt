package com.example.andranfitmobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.databinding.FragmentHomeBinding
import com.example.andranfitmobile.ui.workouts.WorkoutAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var workoutAdapter: WorkoutAdapter
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("USER_ID")
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

        // Configurar RecyclerView para mostrar los próximos workouts
        val recyclerView: RecyclerView = binding.workoutsRecyclerView
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
            binding.bienvenidaTextView.text = "$bienvenida, $nombre"
        }

        homeViewModel.proximosWorkouts.observe(viewLifecycleOwner) { proximosWorkouts ->
            workoutAdapter.submitList(proximosWorkouts)
        }

        // Cargar el usuario y los próximos workouts
        userId?.let { homeViewModel.cargarUsuario(it) }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(userId: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle().apply {
                putString("USER_ID", userId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}