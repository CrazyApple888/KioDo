package ru.nsu.fit.kiodo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.kiodo.data.dao.TrainingDao
import ru.nsu.fit.kiodo.data.converter.Converter
import ru.nsu.fit.kiodo.data.model.training.TrainingWithExercises
import ru.nsu.fit.kiodo.data.model.xref.TrainingExerciseXRef
import ru.nsu.fit.kiodo.domain.repository.TrainingRepository
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val trainingDao: TrainingDao,
    private val converter: Converter<TrainingWithExercises, TrainingModel>
) : TrainingRepository {
    override suspend fun getTraining(name: String): TrainingModel = withContext(Dispatchers.IO) {
        val training = trainingDao.getTrainingWithExercises(name)
        converter.convertToO(training)
    }

    override suspend fun saveTraining(trainingModel: TrainingModel) = withContext(Dispatchers.IO) {
        val training = converter.convertToI(trainingModel)
        trainingDao.insertTrainingsWithExercises(listOf(training))
    }

    override suspend fun addExerciseToTraining(trainingName: String, exerciseName: String) =
        withContext(Dispatchers.IO) {
            trainingDao.insertXRef(listOf(TrainingExerciseXRef(trainingName, exerciseName)))
        }

    override suspend fun getAllTrainings(): List<TrainingModel> = withContext(Dispatchers.IO) {
        trainingDao.getAllTrains().map { training ->
            converter.convertToO(training)
        }
    }

    override suspend fun incrementNumberCompleted(name: String) = withContext(Dispatchers.IO) {
        trainingDao.incrementNumCompleted(name)
    }

    override suspend fun hasTraining(name: String): Boolean =
        withContext(Dispatchers.IO) {
            trainingDao.hasTraining(name)
        }

    override suspend fun hasAtLeastOneTraining(): Boolean = withContext(Dispatchers.IO) {
        trainingDao.hasAtLeastOneTraining()
    }

    override suspend fun getFavoriteTraining(): TrainingModel = withContext(Dispatchers.IO) {
        getTraining(trainingDao.getTrainingWithMaxCompletes().trainingName)
    }
}