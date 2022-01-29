package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import javax.inject.Inject

class GetExercisesListUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(name: String): List<ExerciseModel> =
        repository.getTraining(name).exercises
}