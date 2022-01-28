package ru.nsu.fit.kiodo.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.nsu.fit.kiodo.R
import ru.nsu.fit.kiodo.databinding.FragmentStatisticsBinding
import ru.nsu.fit.kiodo.presentation.viewmodel.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding get() = _binding!!

    private val viewModel: StatisticsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        binding.topAppBar.setNavigationOnClickListener { navigateBack() }
        initViewModelObservers()
        viewModel.loadStatistics()

        return binding.root
    }

    private fun initViewModelObservers() {
        viewModel.favoriteExercise.observe(viewLifecycleOwner) {
            binding.favoriteExerciseName.text = it.exerciseName
        }
        viewModel.favoriteTraining.observe(viewLifecycleOwner) {
            binding.favoriteTrainingName.text = it.trainingName
        }
        viewModel.trainingsDoneNumber.observe(viewLifecycleOwner) {
            binding.trainingsDone.text = it.toString()
        }
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
