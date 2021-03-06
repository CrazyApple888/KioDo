package ru.nsu.fit.kiodo.data.model.training

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.nsu.fit.kiodo.data.model.xref.TrainingExerciseXRef
import ru.nsu.fit.kiodo.data.model.exercise.Exercise

data class TrainingWithExercises(
    @Embedded val training : Training,
    @Relation(
        parentColumn = "trainingName",
        entity = Exercise::class,
        entityColumn = "exerciseName",
        associateBy = Junction(
            value = TrainingExerciseXRef::class,
            parentColumn = "trainingName",
            entityColumn = "exerciseName"
        )
    )
    val exercises : List<Exercise>
)
