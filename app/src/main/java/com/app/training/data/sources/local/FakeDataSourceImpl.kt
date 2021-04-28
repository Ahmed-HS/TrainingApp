package com.app.training.data.sources.local

import com.app.training.data.model.*
import com.app.training.data.sources.AppDataSource
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.random.Random
import kotlin.random.nextInt

object FakeDataSourceImpl : AppDataSource {


    override fun getExercises(): List<Exercise> {
        val manImg = "https://www.voiceboxagency.co.uk/wp-content/uploads/2019/09/Vlad-T.png"
        val womenimg = "https://www.himalmag.com/wp-content/uploads/2019/07/sample-profile-picture.png"
        val zoneId = ZoneId.of("Asia/Kolkata")
        val dateNight = ZonedDateTime.of(2021, 3, 4, 19, 0, 0, 0, zoneId)
        val dateMorning = ZonedDateTime.of(2021, 3, 4, 6, 0, 0, 0, zoneId)
        val duration = Duration.ofMinutes(30)
        var id = 1


        val exercises = listOf(
                Exercise(
                        id, "21-Day Yoga for Beginners",
                        "Naveen & Gaurav | Begginner",
                        dateNight,
                        duration,
                        setOf("yoga","Strength"),
                        manImg,Gender.Male,
                        "Address 1"
                ),
                Exercise(
                        id + 1, "Dance Fitness",
                        "Tom | Begginner",
                        dateNight,
                        duration.plusMinutes(30),
                        setOf("Dance"),
                        womenimg,Gender.Female,
                        "Address 1"
                ),

                Exercise(
                        id + 2, "Cardio Upper Body",
                        "Rahul Huidrom | Intermediate",
                        dateNight,
                        duration.plusMinutes(20),
                        setOf("Cardio"),
                        manImg, Gender.Male,
                        "Address 1"
                ),

                Exercise(
                        id + 3, "Walk Fitness",
                        "Tom | Begginner",
                        dateNight,
                        duration.plusMinutes(10),
                        setOf("Strength"),
                        manImg, Gender.Male,
                        "Address 2"
                ),

                Exercise(
                        id + 4, "21-Day Yoga for Beginners",
                        "Naveen & Gaurav | Begginner",
                        dateNight,
                        duration,
                        setOf("Yoga"),
                        womenimg, Gender.Female,
                        "Address 1"
                ),

                Exercise(
                        id + 5, "Walk Fitness",
                        "Tom | Begginner",
                        dateNight.plusMinutes(31),
                        duration.plusMinutes(10),
                        setOf("Strength"),
                        womenimg,Gender.Female,
                        "Address 2"
                ),

                Exercise(
                        id + 6, "Dance Fitness",
                        "Tom | Begginner",
                        dateNight.plusMinutes(31),
                        duration.plusMinutes(30),
                        getExerciseFilters(),
                        manImg, Gender.Male,
                        "Address 2"
                ),

                Exercise(
                        id + 7, "21-Day Yoga for Beginners",
                        "Naveen & Gaurav | Begginner",
                        dateNight.plusMinutes(31),
                        duration,
                        setOf("S&C","Yoga"),
                        manImg, Gender.Male,
                        "Address 3"
                ),

                Exercise(
                        id + 8, "Cardio Upper Body",
                        "Rahul Huidrom | Intermediate",
                        dateNight.plusMinutes(31),
                        duration.plusMinutes(20),
                        setOf("S&C","Cardio"),
                        womenimg,Gender.Female,
                        "Address 3"
                ),

                Exercise(
                        id + 9, "Walk Fitness",
                        "Tom | Begginner",
                        dateMorning.plusMinutes(31),
                        duration.plusMinutes(10),
                        setOf("Strength"),
                        womenimg,Gender.Female,
                        "Address 1"
                ),

                Exercise(
                        id + 10, "Dance Fitness",
                        "Tom | Begginner",
                        dateMorning.plusMinutes(31),
                        duration.plusMinutes(30),
                        getExerciseFilters(),
                        manImg, Gender.Male,
                        "Address 3"
                ),

                Exercise(
                        id + 11, "21-Day Yoga for Beginners",
                        "Naveen & Gaurav | Begginner",
                        dateMorning.plusMinutes(31),
                        duration,
                        setOf("S&C","Yoga"),
                        manImg, Gender.Male,
                        "Address 4"
                ),

                Exercise(
                        id + 12, "Cardio Upper Body",
                        "Rahul Huidrom | Intermediate",
                        dateMorning.plusMinutes(31),
                        duration.plusMinutes(20),
                        setOf("S&C","Cardio"),
                        womenimg, Gender.Female,
                        "Address 4"
                )

        )
        return exercises
    }

    override fun getExerciseFilters(): Set<String> {
        val filters  = setOf("Dance", "S&C", "Yoga", "Cardio", "Strength")
        return  filters
    }

    override fun getTrainingCenters(): List<Center> {
        val centerList = mutableListOf<Center>()

        val addressList = mutableListOf<String>()

        repeat(10)
        {
            addressList.add("Address $it")
        }

        centerList.add(Center(1,"Center ${1}",1, getExercises()))
        centerList.add(Center(1,"Center ${2}",1, getExercises().takeLast(10)))

        repeat(20)
        {
            centerList.add(Center(it+3,"Center ${it + 2}",it+1,listOf()))
        }

        return centerList
    }

    override fun getExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()

        repeat(20)
        {
            val expense = Expense("Amy", ZonedDateTime.now(),500,"0143907942$it","")
            expenses.add(expense)
        }

        return expenses
    }

    override fun getDashboardActions(): List<DashboardAction> {
        val actions = mutableListOf<DashboardAction>()

        repeat(7)
        {
            val action = DashboardAction(it + 1,(it + 1) * 67,"Action ${it +1 }")
            actions.add(action)
        }

        return actions
    }

    override fun getBooths(): List<Booth> {

        val booths = mutableListOf<Booth>()

        repeat(20)
        {
            val maxProgress = Random.nextInt(100..1000)
            val currentProgress = Random.nextInt(1..maxProgress)
            val number = Random.nextInt(20..99)

            val item = Booth("Booth ${it + 1}",
                "Polling Station Name",
                currentProgress,
                maxProgress,
                number)

            booths.add(item)
        }

        return booths
    }

    override fun getOrders(): List<Order> {
        val orders = mutableListOf<Order>()

        repeat(20)
        {

            val quantity = Random.nextInt(20..99)

            val item = Order("Jaipur",quantity,"")

            orders.add(item)
        }

        return orders
    }

    override fun getExpensesReport(): List<ExpensesReport> {
        val reports = mutableListOf<ExpensesReport>()

        repeat(10)
        {

            val income = Random.nextInt(10000..900000)
            val expenses = Random.nextInt(10000..900000)

            val item = ExpensesReport(income,expenses)

            reports.add(item)
        }

        return reports
    }

    override fun getDeliveries(): List<Delivery> {
        val deliveries = mutableListOf<Delivery>()

        repeat(20)
        {
            val expense = Delivery("Delivery ${it + 1}","way to deliver tour order","0143907942$it")
            deliveries.add(expense)
        }

        return deliveries
    }
}