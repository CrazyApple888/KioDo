package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import javax.inject.Inject

class CheckIsAtLeastOneTrainingExist @Inject constructor(
    private val repository: TrainingRepository
) {

    suspend operator fun invoke(): Boolean =
        repository.hasAtLeastOneTraining()

}