package ru.nsu.fit.kiodo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.kiodo.data.converter.Converter
import ru.nsu.fit.kiodo.data.dao.ExerciseDao
import ru.nsu.fit.kiodo.data.model.exercise.Exercise
import ru.nsu.fit.kiodo.domain.repository.ExerciseRepository
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val converter: Converter<Exercise, ExerciseModel>
) : ExerciseRepository {
    override suspend fun saveExercise(exerciseModel: ExerciseModel) = withContext(Dispatchers.IO) {
        exerciseDao.insertExercises(listOf(converter.convertToI(exerciseModel)))
    }

    override suspend fun incrementNumberCompleted(name: String) = withContext(Dispatchers.IO) {
        exerciseDao.incrementNumCompleted(name)
    }

    override suspend fun getAllExercises(): List<ExerciseModel> = withContext(Dispatchers.IO) {
        exerciseDao.getAllExercises().map { converter.convertToO(it) }
    }

    override suspend fun getFavoriteExercise(): ExerciseModel = withContext(Dispatchers.IO) {
        converter.convertToO(exerciseDao.getExerciseWithMaxCompletes())
    }
}