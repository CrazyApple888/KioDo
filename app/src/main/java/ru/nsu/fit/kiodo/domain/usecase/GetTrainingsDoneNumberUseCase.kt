package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import javax.inject.Inject

class GetTrainingsDoneNumberUseCase @Inject constructor(
    private val repository: TrainingRepository
) {

    suspend operator fun invoke(): Int =
        repository.getAllTrainings().sumOf { it.numberCompleted }

}