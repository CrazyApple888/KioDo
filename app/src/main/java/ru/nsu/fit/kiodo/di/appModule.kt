package ru.nsu.fit.kiodo.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module
import ru.nsu.fit.kiodo.data.KioDoDatabase
import ru.nsu.fit.kiodo.data.converter.Converter
import ru.nsu.fit.kiodo.data.converter.TrainingConverter
import ru.nsu.fit.kiodo.data.model.training.TrainingWithExercises
import ru.nsu.fit.kiodo.data.repository.TrainingRepositoryImpl
import ru.nsu.fit.kiodo.domain.TrainingRepository
import ru.nsu.fit.kiodo.domain.model.TrainingModel
import ru.nsu.fit.kiodo.domain.usecase.GetAllTrainingUseCase
import ru.nsu.fit.kiodo.presentation.viewmodel.MainViewModel

val appModule = module {

    viewModel { MainViewModel() }

    single<KioDoDatabase> {
        Room.databaseBuilder(
            androidContext(),
            KioDoDatabase::class.java,
            "Kio"
        )
            .build()
    }

    single { get<KioDoDatabase>(KioDoDatabase::class.java).trainingDao() }
    factory<Converter<TrainingWithExercises, TrainingModel>> { TrainingConverter() }
    single<TrainingRepository> { TrainingRepositoryImpl(get(), get()) }

    factory { GetAllTrainingUseCase(get()) }

}