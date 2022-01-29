package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.model.TrainingModel
import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import javax.inject.Inject

class GetFavoriteTrainingUseCase @Inject constructor(
    private val repository: TrainingRepository
) {

    suspend operator fun invoke(): TrainingModel =
        repository.getFavoriteTraining()
}