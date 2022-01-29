package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import javax.inject.Inject

class SaveTrainingUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(name: String, restBtwExes: Int, exercises: List<ExerciseModel>) {
        val training = TrainingModel(name, restBtwExes, 0, exercises)
        repository.saveTraining(training)
    }
}