package ru.nsu.fit.kiodo.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.kiodo.KioDoApp
import ru.nsu.fit.kiodo.R
import ru.nsu.fit.kiodo.databinding.FragmentTrainingListBinding
import ru.nsu.fit.kiodo.presentation.viewmodel.TrainingListViewModel
import ru.nsu.fit.kiodo.ui.adapter.TrainingListAdapter
import javax.inject.Inject


class TrainingListFragment : Fragment() {

    private var _binding: FragmentTrainingListBinding? = null
    private val binding: FragmentTrainingListBinding get() = _binding!!
    private val adapter = TrainingListAdapter { navigateToTraining(it) }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TrainingListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as KioDoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory)[TrainingListViewModel::class.java]

        _binding = FragmentTrainingListBinding.inflate(inflater, container, false)

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            trainingRecyclerView.adapter = adapter
            newTrainingButton.setOnClickListener { navigateToTrainEditing() }
            topAppBar.setOnMenuItemClickListener { onMenuItemSelected(it) }
        }
        viewModel.trainings.observe(viewLifecycleOwner) { trainings ->
            binding.emptyTrainingText.isGone = trainings.isNotEmpty()
            adapter.trainings = trainings
        }
        viewModel.getAllTrainings()
    }

    private fun navigateToTrainEditing(trainingName: String? = null) {
        val bundle = Bundle()
        bundle.putString(TrainEditingFragment.trainingNameKey, trainingName)
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, TrainEditingFragment::class.java, bundle)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToStatisticsScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, StatisticsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.profile -> {
                navigateToStatisticsScreen()
                true
            }
            else -> false
        }

    private fun navigateToTraining(trainingName: String) {
        val bundle = Bundle()
        bundle.putString(TrainingFragment.trainingNameKey, trainingName)
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, TrainingFragment::class.java, bundle)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}