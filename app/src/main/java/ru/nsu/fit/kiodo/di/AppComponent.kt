package ru.nsu.fit.kiodo.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.kiodo.ui.fragment.*
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(fragment: StatisticsFragment)

    fun inject(fragment: ExerciseEditingFragment)

    fun inject(fragment: ExerciseListFragment)

    fun inject(fragment: TrainEditingFragment)

    fun inject(fragment: TrainingFragment)

    fun inject(fragment: TrainingListFragment)

    @Component.Builder
    interface AppComponentBuilder {

        fun context(@BindsInstance context: Context): AppComponentBuilder

        fun dataModule(module: DataModule): AppComponentBuilder

        fun build(): AppComponent
    }

}