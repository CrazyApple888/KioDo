package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import javax.inject.Inject

class GetAllTrainingUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(): List<TrainingModel> =
        repository.getAllTrainings()
}