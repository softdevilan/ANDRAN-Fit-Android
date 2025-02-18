package com.example.andranfitmobile.ui.workouts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.SharedViewModel
import com.example.andranfitmobile.data.Workout
import com.example.andranfitmobile.databinding.FragmentWorkoutsBinding

private const val TAG = "WorkoutsFragment"

class WorkoutsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding: FragmentWorkoutsBinding? = null
    private val binding get() = _binding!!

    private lateinit var completarButton: Button

    private lateinit var workoutsViewModel: WorkoutsViewModel
    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var pastWorkoutAdapter: WorkoutAdapter

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate: Iniciando WorkoutsFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workoutsViewModel = ViewModelProvider(this)[WorkoutsViewModel::class.java]
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
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
            val pastWorkoutsRecyclerView: RecyclerView = binding.completedWorkoutsRecyclerView
            pastWorkoutAdapter = WorkoutAdapter(true, userId)
            pastWorkoutsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            pastWorkoutsRecyclerView.adapter = pastWorkoutAdapter

            // Observar los workouts futuros
            workoutsViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
                workoutAdapter.submitList(workouts)

                // Mostrar u ocultar el mensaje de "No tienes workouts pendientes"
                binding.noUpcomingWorkoutsText.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
                binding.workoutRecyclerView.visibility = if (workouts.isEmpty()) View.GONE else View.VISIBLE
            }

            // Observar los workouts pasados
            workoutsViewModel.pastWorkouts.observe(viewLifecycleOwner) { pastWorkouts ->
                pastWorkoutAdapter.submitList(pastWorkouts)

                // Mostrar u ocultar el mensaje de "No tienes workouts completados"
                binding.noCompletedWorkoutsText.visibility = if (pastWorkouts.isEmpty()) View.VISIBLE else View.GONE
                binding.completedWorkoutsRecyclerView.visibility = if (pastWorkouts.isEmpty()) View.GONE else View.VISIBLE
            }

            Log.d(TAG, "Llamando a cargarWorkouts()")
            workoutsViewModel.cargarWorkouts(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Destruyendo WorkoutsFragment")
    }

}