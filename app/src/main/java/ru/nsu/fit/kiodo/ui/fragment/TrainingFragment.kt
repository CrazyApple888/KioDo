package ru.nsu.fit.kiodo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.kiodo.KioDoApp
import ru.nsu.fit.kiodo.R
import ru.nsu.fit.kiodo.databinding.FragmentTrainingBinding
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.presentation.viewmodel.TrainingViewModel
import ru.nsu.fit.kiodo.ui.adapter.ExerciseListAdapter
import javax.inject.Inject

class TrainingFragment : Fragment() {

    companion object {
        const val trainingNameKey = "TRAININGNAMEKEY"
    }

    private var _binding: FragmentTrainingBinding? = null
    private val binding: FragmentTrainingBinding get() = _binding!!

    private var trainingName: String? = null

    private val adapter = ExerciseListAdapter()
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TrainingViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as KioDoApp).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this, viewModelFactory)[TrainingViewModel::class.java]

        _binding = FragmentTrainingBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            arguments?.let {
                trainingName = it.getString(trainingNameKey)
            }
        } else {
            trainingName = savedInstanceState.getString(trainingNameKey)
        }

        viewModel.exerciseCount.observe(viewLifecycleOwner) { count ->
            binding.trainingPPar.max = count
        }

        if (trainingName != null) {
            viewModel.load(trainingName!!)
            binding.topAppBar.title = trainingName
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.training_name_not_found_toast_text),
                Toast.LENGTH_SHORT
            ).show()
            navigateBack()
        }

        binding.nextExerciseButton.setOnClickListener {
            viewModel.getNextExercise()
        }
        binding.trainingScreenRecycleView.adapter = adapter

        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            adapter.exercises = exercises
            if (exercises.isEmpty() && viewModel.isTrainingStarted) {
                binding.nextExerciseButton.text = getString(R.string.last_exercise_button_text)
                binding.recycleTitle.isGone = true
            } else {
                binding.nextExerciseButton.text = getString(R.string.next_exercise_button_text)
                binding.recycleTitle.isGone = false
            }
        }

        viewModel.isFinished.observe(viewLifecycleOwner) { isFinished ->
            if (isFinished) {
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.currentExercise.observe(viewLifecycleOwner) { currentExercise ->
            updateCurrentExercise(currentExercise)
            binding.trainingPPar.incrementProgressBy(1)
        }
        binding.topAppBar.setNavigationOnClickListener { navigateBack() }

        return binding.root
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun updateCurrentExercise(newExercise: ExerciseModel) {
        binding.exerciseName.text = newExercise.exerciseName
        binding.repeatsCount.text = newExercise.repeats.toString()
        if (newExercise.equipment == null || newExercise.equipment.isBlank()) {
            binding.equipmentTitle.isGone = true
            binding.equipment.isGone = true
        } else {
            binding.equipmentTitle.isGone = false
            binding.equipment.isGone = false
            binding.equipment.text = newExercise.equipment
        }
        if (newExercise.description == null || newExercise.description.isBlank()) {
            binding.descriptionTitle.isGone = true
            binding.description.isGone = true
        } else {
            binding.descriptionTitle.isGone = false
            binding.description.isGone = false
            binding.description.text = newExercise.description
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        trainingName?.let {
            outState.putString(trainingNameKey, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}