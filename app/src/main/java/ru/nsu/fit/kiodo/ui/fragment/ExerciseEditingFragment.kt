package ru.nsu.fit.kiodo.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.kiodo.KioDoApp
import ru.nsu.fit.kiodo.R
import ru.nsu.fit.kiodo.presentation.viewmodel.ExerciseEditingViewModel
import ru.nsu.fit.kiodo.databinding.FragmentExerciseEditingBinding
import ru.nsu.fit.kiodo.presentation.viewmodel.TrainEditingSharedViewModel
import javax.inject.Inject

class ExerciseEditingFragment : Fragment() {

    private var _binding: FragmentExerciseEditingBinding? = null
    private val binding: FragmentExerciseEditingBinding get() = _binding!!

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ExerciseEditingViewModel
    private lateinit var sharedViewModel: TrainEditingSharedViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as KioDoApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this, viewModelFactory)[ExerciseEditingViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[TrainEditingSharedViewModel::class.java]

        _binding = FragmentExerciseEditingBinding.inflate(inflater, container, false)

        initTextListeners()
        initButtons()
        return binding.root
    }

    private fun initTextListeners() {
        with(binding) {
            exerciseNameEditText.addTextChangedListener {
                viewModel.exerciseName = exerciseNameEditText.text.toString()
            }
            exerciseRepeatsEditText.addTextChangedListener {
                val value = exerciseRepeatsEditText.text.toString()
                viewModel.numberOfRepeats = if (value.isNotBlank()) value.toInt() else 0
            }
            exerciseRestEditText.addTextChangedListener {
                val value = exerciseRestEditText.text.toString()
                viewModel.restBetweenRepeats = if (value.isNotBlank()) value.toInt() else 0
            }
            exerciseEquipmentEditText.addTextChangedListener {
                viewModel.equipment = exerciseEquipmentEditText.text.toString()
            }
            exerciseDescriptionEditText.addTextChangedListener {
                viewModel.description = exerciseDescriptionEditText.text.toString()
            }
        }
    }

    private fun initButtons() {
        with(binding) {
            topAppBar.setNavigationOnClickListener { navigateBack() }
            topAppBar.setOnMenuItemClickListener { onMenuItemClickListener(it) }
        }
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun navigateToTrainEditing() {
        parentFragmentManager.popBackStack(
            TrainEditingFragment.backstack,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    private fun onMenuItemClickListener(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.done -> {
                saveExercise()
                true
            }
            else -> false
        }

    private fun saveExercise() {
        if (viewModel.saveExercise()) {
            sharedViewModel.selectExercise(viewModel.getExercise())
            binding.progressCircular.isGone = false
            viewModel.isSaved.observe(viewLifecycleOwner) {
                if (it) {
                    navigateToTrainEditing()
                } else {
                    showInvalidExerciseDataToast()
                }
            }
        } else {
            showInvalidExerciseDataToast()
        }
    }

    private fun showInvalidExerciseDataToast() =
        Toast.makeText(
            requireContext(),
            getString(R.string.invalid_exercise_data),
            Toast.LENGTH_SHORT
        ).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}