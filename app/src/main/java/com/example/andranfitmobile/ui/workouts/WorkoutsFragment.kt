package com.example.andranfitmobile.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.R
import com.example.andranfitmobile.data.Workout
import com.example.andranfitmobile.databinding.FragmentWorkoutsBinding
import com.example.andranfitmobile.ui.workouts.WorkoutAdapter

class WorkoutsFragment : Fragment() {

    private var _binding: FragmentWorkoutsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var workoutsViewModel: WorkoutsViewModel
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
        workoutsViewModel = ViewModelProvider(this).get(WorkoutsViewModel::class.java)
        _binding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar RecyclerView para mostrar los workouts
        val recyclerView: RecyclerView = binding.workoutRecyclerView
        workoutAdapter = WorkoutAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = workoutAdapter

        // Observar los workouts
        workoutsViewModel.workouts.observe(viewLifecycleOwner) { workouts ->
            workoutAdapter.submitList(workouts)
        }

        // Cargar los workouts del usuario
        userId?.let { workoutsViewModel.cargarWorkouts(it) }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(userId: String?): WorkoutsFragment {
            val fragment = WorkoutsFragment()
            val args = Bundle().apply {
                putString("USER_ID", userId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}