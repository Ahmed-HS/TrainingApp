package com.app.training.data.model

import java.time.Duration
import java.time.ZonedDateTime

enum class TimeFilter {
    Morning,Evening
}

enum class Gender {
    Male,Female,None
}

data class Exercise constructor(
    val id:Int,
    val title:String,
    val description:String,
    val date: ZonedDateTime,
    val duration: Duration,
    val categories: Set<String>,
    val imgURL:String = "https://picsum.photos/200",
    val gender: Gender,
    val address: String = "Address $id"
)

fun Collection<Exercise>.filterByAddress(address:String): List<Exercise>
{
    val addressLower = address.toLowerCase()
    return this.filter { it.address.toLowerCase() == addressLower }
}

fun Collection<Exercise>.filterByCategories(categories: Set<String>) : List<Exercise>
{
    val filtersLowerCase = categories.map { it.toLowerCase() }.toSet()
    return  filter {
        it.categories.map { it.toLowerCase() }
                .toSet()
                .any { category -> filtersLowerCase.contains(category) }
    }
}

fun Collection<Exercise>.filterByTime(filter: TimeFilter) : List<Exercise>
{
    return if(filter == TimeFilter.Morning)
    {
        this.filter { it.date.hour in 0..15 }
    }
    else
    {
        this.filter { it.date.hour in 16..24 }
    }
}

fun Collection<Exercise>.filterByGender(gender: Gender) : List<Exercise>
{
    return when (gender) {
        Gender.Male -> {
            this.filter { it.gender == Gender.Male }
        }
        Gender.Female -> {
            this.filter { it.gender == Gender.Female }
        }
        else -> {
            this.toList()
        }
    }
}