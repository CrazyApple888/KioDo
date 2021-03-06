package ru.nsu.fit.kiodo.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.kiodo.KioDoApp
import ru.nsu.fit.kiodo.databinding.FragmentStatisticsBinding
import ru.nsu.fit.kiodo.presentation.viewmodel.StatisticsViewModel
import javax.inject.Inject

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: StatisticsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as KioDoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]

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
