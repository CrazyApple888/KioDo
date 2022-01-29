package ru.nsu.fit.kiodo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import ru.nsu.fit.kiodo.domain.usecase.GetAllTrainingUseCase
import javax.inject.Inject

class TrainingListViewModel @Inject constructor(
    private val getAllTrainingUseCase: GetAllTrainingUseCase
) : ViewModel() {

    private val _trainings: MutableLiveData<List<TrainingModel>> = MutableLiveData()
    val trainings: LiveData<List<TrainingModel>> get() = _trainings

    fun getAllTrainings() {
        viewModelScope.launch {
            _trainings.value = getAllTrainingUseCase()!!
        }
    }

}