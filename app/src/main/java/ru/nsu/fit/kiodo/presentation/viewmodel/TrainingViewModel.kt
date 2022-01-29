package ru.nsu.fit.kiodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.usecase.GetExercisesListUseCase
import ru.nsu.fit.kiodo.domain.usecase.IncrementExerciseNumberCompletedUseCase
import ru.nsu.fit.kiodo.domain.usecase.IncrementTrainingNumberCompletedUseCase
import javax.inject.Inject

class TrainingViewModel @Inject constructor(
    private val getExercisesListUseCase: GetExercisesListUseCase,
    private val incrementExerciseNumberCompletedUseCase: IncrementExerciseNumberCompletedUseCase,
    private val incrementTrainingNumberCompletedUseCase: IncrementTrainingNumberCompletedUseCase
) : ViewModel() {

    var trainingName: String = ""
    var isTrainingStarted = false
        private set

    private val _currentExercise: MutableLiveData<ExerciseModel> = MutableLiveData()
    val currentExercise: LiveData<ExerciseModel> get() = _currentExercise

    private val _exercises: MutableLiveData<List<ExerciseModel>> = MutableLiveData(emptyList())
    val exercises: LiveData<List<ExerciseModel>> get() = _exercises

    private val _exerciseCount: MutableLiveData<Int> = MutableLiveData()
    val exerciseCount: LiveData<Int> get() = _exerciseCount

    private val _isFinished: MutableLiveData<Boolean> = MutableLiveData()
    val isFinished: LiveData<Boolean> get() = _isFinished


    fun getNextExercise() {
        if (exercises.value!!.isEmpty()) {
            viewModelScope.launch {
                incrementTrainingNumberCompletedUseCase(trainingName)
                _isFinished.value = true
            }
            return
        }
        val nextExercise = _exercises.value?.first()!!
        viewModelScope.launch {
            incrementExerciseNumberCompletedUseCase(nextExercise.exerciseName)
        }
        _exercises.value = _exercises.value?.drop(1)
        _currentExercise.value = nextExercise
    }

    fun load(name: String) {
        trainingName = name
        viewModelScope.launch {
            _exercises.value = getExercisesListUseCase(name)!!
            _exerciseCount.value = exercises.value?.size
            isTrainingStarted = true
            getNextExercise()
        }
    }
}