package ru.nsu.fit.kiodo.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import ru.nsu.fit.kiodo.data.repository.ExerciseRepositoryImpl
import ru.nsu.fit.kiodo.data.repository.TrainingRepositoryImpl
import ru.nsu.fit.kiodo.domain.repository.ExerciseRepository
import ru.nsu.fit.kiodo.domain.repository.TrainingRepository

@Module
interface RepositoryModule {

    @Binds
    fun bindTrainingRepository(repo: TrainingRepositoryImpl): TrainingRepository

    @Binds
    fun bindExerciseRepository(repo: ExerciseRepositoryImpl): ExerciseRepository

}