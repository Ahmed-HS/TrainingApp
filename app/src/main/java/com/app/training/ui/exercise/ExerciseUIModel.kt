package com.app.training.ui.exercise

import com.app.training.data.model.Exercise
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

sealed class ExerciseUIModel {
    abstract val id: Int

    data class TimeSeparator(val time: String) : ExerciseUIModel() {
        override val id: Int = Int.MIN_VALUE
    }

    data class ExerciseItem(val exercise: Exercise) : ExerciseUIModel() {
        override val id: Int = exercise.id
    }
}

fun exercisesToUIModel(exercises: List<Exercise>) : List<ExerciseUIModel>
{
    if(exercises.isEmpty()) return emptyList()

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val halfhour = 30
    val result = mutableListOf<ExerciseUIModel>()

    result.add(ExerciseUIModel.TimeSeparator(formatter.format(exercises[0].date)))
    var currentDate = exercises[0].date
    result.add(ExerciseUIModel.ExerciseItem(exercises[0]))

    for (i in 1 until exercises.size)
    {
        val diff = Duration.between(currentDate, exercises[i].date).abs().toMinutes()

        if(diff >= halfhour)
        {
            val time = exercises[i].date.truncatedTo(ChronoUnit.HOURS)
                    .plusMinutes((halfhour * (exercises[i].date.minute / halfhour).toLong()))

            result.add(ExerciseUIModel.TimeSeparator(formatter.format(time)))

            currentDate = exercises[i].date

        }

        result.add(ExerciseUIModel.ExerciseItem(exercises[i]))
    }

    return result
}

