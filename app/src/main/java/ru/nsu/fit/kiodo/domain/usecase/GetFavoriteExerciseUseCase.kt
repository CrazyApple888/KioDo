package ru.nsu.fit.kiodo.domain.usecase

import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetFavoriteExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {

    suspend operator fun invoke(): ExerciseModel =
        repository.getFavoriteExercise()

}