package ru.nsu.fit.kiodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nsu.fit.kiodo.data.dao.ExerciseDao
import ru.nsu.fit.kiodo.data.dao.TrainingDao
import ru.nsu.fit.kiodo.data.model.exercise.Exercise
import ru.nsu.fit.kiodo.data.model.training.Training
import ru.nsu.fit.kiodo.data.model.xref.TrainingExerciseXRef

@Database(entities = [Exercise::class,
                      Training::class,
                      TrainingExerciseXRef::class],
            version = 1)
abstract class KioDoDatabase : RoomDatabase() {
    abstract fun exerciseDao() : ExerciseDao
    abstract fun trainingDao() : TrainingDao
}