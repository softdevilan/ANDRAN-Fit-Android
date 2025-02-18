package com.example.andranfitmobile.ui.workouts

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
import com.example.andranfitmobile.databinding.FragmentWorkoutsBinding

private const val TAG = "WorkoutsFragment"

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
            Log.d(TAG, "ID de usuario recibido1: ${userId}")
        }

        Log.d(TAG, "ID de usuario recibido2: ${userId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "ID de usuario recibido3: ${userId}")
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
        userId?.let {
            workoutsViewModel.cargarWorkouts(it)
            Log.d(TAG, "Cargando workouts para usuario con ID: $it")
        } ?: run {
            Log.e(TAG, "UserID es null, no se pueden cargar los workouts")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Destruyendo WorkoutsFragment")
    }

    companion object {
        fun newInstance(userId: String?): WorkoutsFragment {
            val fragment = newInstance(userId)
            val args = Bundle().apply {
                putString("USER_ID", userId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}