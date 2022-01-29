package ru.nsu.fit.kiodo.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.kiodo.KioDoApp
import ru.nsu.fit.kiodo.R
import ru.nsu.fit.kiodo.databinding.FragmentTrainEditingBinding
import ru.nsu.fit.kiodo.presentation.viewmodel.TrainEditingSharedViewModel
import ru.nsu.fit.kiodo.ui.adapter.ExerciseListAdapter
import javax.inject.Inject

class TrainEditingFragment : Fragment() {

    companion object {
        const val trainingNameKey = "TRAININGNAMEKEY"
        const val backstack = "BACK"
    }

    private var _binding: FragmentTrainEditingBinding? = null
    private val binding: FragmentTrainEditingBinding get() = _binding!!

    private val adapter = ExerciseListAdapter()
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TrainEditingSharedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as KioDoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[TrainEditingSharedViewModel::class.java]

        _binding = FragmentTrainEditingBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            arguments?.let {
                viewModel.trainingName = it.getString(trainingNameKey) ?: ""
            }
        } else {
            viewModel.trainingName =
                savedInstanceState.getString(trainingNameKey) ?: ""
        }
        with(binding) {
            if (viewModel.trainingName != "") {
                editTrainingName.setText(viewModel.trainingName)
                editTrainingName.isEnabled = false
            }
            trainEditingRecyclerView.adapter = adapter
        }


        initListeners()

        viewModel.isValidated.observe(viewLifecycleOwner) { isValidated ->
            if (!isValidated) {
                showInvalidTrainingDataToast()
            }
        }

        viewModel.isSaved.observe(viewLifecycleOwner) { isSaved ->
            if (isSaved) {
                navigateBack()
            }
        }

        viewModel.selectedExercises.observe(viewLifecycleOwner) { exercises ->
            adapter.exercises = exercises
        }

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            addExerciseButton.setOnClickListener { navigateToExerciseList() }
            editTrainingName.addTextChangedListener {
                viewModel.trainingName = editTrainingName.text.toString()
            }
            topAppBar.setOnMenuItemClickListener { onMenuItemSelected(it) }
            topAppBar.setNavigationOnClickListener { navigateBack() }
            editRestBetweenExercises.addTextChangedListener {
                val value = editRestBetweenExercises.text.toString()
                viewModel.restBetweenExercises = if (value.isNotBlank()) value.toInt() else 0
            }
        }
    }

    private fun navigateToExerciseList() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, ExerciseListFragment())
            .addToBackStack(backstack)
            .commit()
    }

    private fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.done -> {
                saveTrainingAndReturn()
                true
            }
            else -> false
        }


    private fun saveTrainingAndReturn() {
        viewModel.saveTraining()
    }

    private fun showInvalidTrainingDataToast() =
        Toast.makeText(
            requireContext(),
            getString(R.string.invalid_training_data),
            Toast.LENGTH_SHORT
        ).show()

    private fun navigateBack() {
        viewModel.clear()
        parentFragmentManager.popBackStack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(trainingNameKey, viewModel.trainingName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}