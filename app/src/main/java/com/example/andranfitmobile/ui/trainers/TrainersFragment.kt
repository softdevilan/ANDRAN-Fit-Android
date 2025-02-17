package com.example.andranfitmobile.ui.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.andranfitmobile.databinding.FragmentTrainersBinding

class TrainersFragment : Fragment() {

    private var _binding: FragmentTrainersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val trainersViewModel =
            ViewModelProvider(this).get(TrainersViewModel::class.java)

        _binding = FragmentTrainersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTrainers
        trainersViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}