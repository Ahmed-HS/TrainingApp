package com.app.training.data.sources

import com.app.training.data.model.*


interface AppDataSource {

    fun getExercises(): List<Exercise>

    fun getExerciseFilters() : Set<String>

    fun getTrainingCenters() : List<Center>

    fun getExpenses() : List<Expense>

    fun getDashboardActions() : List<DashboardAction>

    fun getBooths() : List<Booth>

    fun getOrders() : List<Order>

    fun getExpensesReport() : List<ExpensesReport>

    fun getDeliveries() : List<Delivery>
}