package com.example.andranfitmobile.ui.trainers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andranfitmobile.databinding.FragmentTrainersBinding
import com.example.andranfitmobile.ui.trainers.adapter.TrainerAdapter

class TrainersFragment : Fragment() {

    private var _binding: FragmentTrainersBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var trainersViewModel: TrainersViewModel
    private lateinit var trainerAdapter: TrainerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        trainersViewModel = ViewModelProvider(this).get(TrainersViewModel::class.java)
        _binding = FragmentTrainersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar RecyclerView para mostrar los entrenadores
        val recyclerView: RecyclerView = binding.trainerRecyclerView
        trainerAdapter = TrainerAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = trainerAdapter

        // Observar los entrenadores
        trainersViewModel.entrenadores.observe(viewLifecycleOwner) { entrenadores ->
            trainerAdapter.submitList(entrenadores)
        }

        // Cargar los entrenadores
        trainersViewModel.cargarEntrenadores()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}