package ru.nsu.fit.kiodo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nsu.fit.kiodo.presentation.viewmodel.*

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    fun bindStatisticsViewModel(viewModel: StatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseEditingViewModel::class)
    fun bindExerciseEditingViewModel(viewModel: ExerciseEditingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainEditingSharedViewModel::class)
    fun bindTrainEditingSharedViewModel(viewModel: TrainEditingSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingListViewModel::class)
    fun bindTrainingListViewModel(viewModel: TrainingListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingViewModel::class)
    fun bindTrainingViewModel(viewModel: TrainingViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}