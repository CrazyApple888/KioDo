package ru.nsu.fit.kiodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.usecase.SaveExerciseUseCase
import javax.inject.Inject

class ExerciseEditingViewModel @Inject constructor(
    private val saveExerciseUseCase: SaveExerciseUseCase
) : ViewModel() {
    var exerciseName: String = ""
    var numberOfRepeats: Int = 0
    var restBetweenRepeats: Int = 0
    var equipment: String = ""
    var description: String = ""

    private var _isSaved = MutableLiveData<Boolean>()
    val isSaved: LiveData<Boolean> get() = _isSaved

    fun saveExercise(): Boolean {
        if (!validateData()) {
            return false
        }
        viewModelScope.launch {
            saveExerciseUseCase(
                getExercise()
            )
            _isSaved.value = true
        }

        return true
    }

    fun getExercise(): ExerciseModel =
        ExerciseModel(
            exerciseName,
            numberOfRepeats,
            restBetweenRepeats,
            equipment,
            description,
            0
        )

    private fun validateData() =
        exerciseName.isNotBlank() && numberOfRepeats != 0 && restBetweenRepeats != 0
}