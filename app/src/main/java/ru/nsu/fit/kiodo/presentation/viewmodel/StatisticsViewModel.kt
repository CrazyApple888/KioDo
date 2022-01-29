package ru.nsu.fit.kiodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import ru.nsu.fit.kiodo.domain.usecase.CheckIsAtLeastOneTrainingExist
import ru.nsu.fit.kiodo.domain.usecase.GetFavoriteExerciseUseCase
import ru.nsu.fit.kiodo.domain.usecase.GetFavoriteTrainingUseCase
import ru.nsu.fit.kiodo.domain.usecase.GetTrainingsDoneNumberUseCase
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    private val getFavoriteTrainingUseCase: GetFavoriteTrainingUseCase,
    private val getFavoriteExerciseUseCase: GetFavoriteExerciseUseCase,
    private val getTrainingDoneNumberUseCase: GetTrainingsDoneNumberUseCase,
    private val checkIsAtLeastOneTrainingExist: CheckIsAtLeastOneTrainingExist
) : ViewModel() {

    private val _favoriteTraining = MutableLiveData<TrainingModel>()
    val favoriteTraining: LiveData<TrainingModel> get() = _favoriteTraining

    private val _favoriteExercise = MutableLiveData<ExerciseModel>()
    val favoriteExercise: LiveData<ExerciseModel> get() = _favoriteExercise

    private val _trainingsDoneNumber = MutableLiveData<Int>()
    val trainingsDoneNumber: LiveData<Int> get() = _trainingsDoneNumber

    fun loadStatistics() {
        viewModelScope.launch {
            if (!checkIsAtLeastOneTrainingExist()) {
                return@launch
            }
            val favEx = async { getFavoriteExerciseUseCase() }
            val favTr = async { getFavoriteTrainingUseCase() }
            val numDone = async { getTrainingDoneNumberUseCase() }

            _favoriteExercise.value = favEx.await()
            _favoriteTraining.value = favTr.await()
            _trainingsDoneNumber.value = numDone.await()
        }
    }

}