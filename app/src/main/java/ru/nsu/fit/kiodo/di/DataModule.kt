package ru.nsu.fit.kiodo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.fit.kiodo.data.KioDoDatabase
import ru.nsu.fit.kiodo.data.converter.Converter
import ru.nsu.fit.kiodo.data.converter.ExerciseConverter
import ru.nsu.fit.kiodo.data.converter.TrainingConverter
import ru.nsu.fit.kiodo.data.dao.ExerciseDao
import ru.nsu.fit.kiodo.data.dao.TrainingDao
import ru.nsu.fit.kiodo.data.model.exercise.Exercise
import ru.nsu.fit.kiodo.data.model.training.TrainingWithExercises
import ru.nsu.fit.kiodo.data.repository.TrainingRepositoryImpl
import ru.nsu.fit.kiodo.domain.model.ExerciseModel
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataModule(
    val context: Context
) {

    private val database = Room.databaseBuilder(
        context,
        KioDoDatabase::class.java,
        "KioDoDatabase"
    )
        .build()

    @Singleton
    @Provides
    fun provideDatabase(): KioDoDatabase {
        return database
    }

    @Singleton
    @Provides
    fun provideTrainingDao(): TrainingDao = database.trainingDao()

    @Singleton
    @Provides
    fun provideExerciseDao(): ExerciseDao = database.exerciseDao()

    @Provides
    fun provideExerciseConverter(): Converter<Exercise, ExerciseModel> = ExerciseConverter()

    @Provides
    fun provideTrainingConverter(): Converter<TrainingWithExercises, TrainingModel> = TrainingConverter()

}