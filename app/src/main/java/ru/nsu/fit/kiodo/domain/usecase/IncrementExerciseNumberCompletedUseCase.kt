package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.ExerciseRepository
import javax.inject.Inject

class IncrementExerciseNumberCompletedUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(name: String) {
        repository.incrementNumberCompleted(name)
    }
}