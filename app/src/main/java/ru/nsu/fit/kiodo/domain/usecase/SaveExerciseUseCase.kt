package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.repository.ExerciseRepository
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import javax.inject.Inject

class SaveExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {

    suspend operator fun invoke(exercise: ExerciseModel) {
        repository.saveExercise(exercise)
    }

}