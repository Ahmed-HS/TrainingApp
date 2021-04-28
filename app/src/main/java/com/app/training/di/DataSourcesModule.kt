package com.app.training.di

import com.app.training.data.model.*
import com.app.training.data.sources.AppDataSource
import com.app.training.data.sources.local.FakeDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class FakeDataSource

@Qualifier
annotation class RemoteDataSource

@InstallIn(SingletonComponent::class)
@Module
object DataSourcesModule {

    @Provides
    @Singleton
    @FakeDataSource
    fun provideFakeDataSource() : AppDataSource{
        return FakeDataSourceImpl
    }

    @Provides
    @Singleton
    @RemoteDataSource
    fun provideRemoteDataSource() : AppDataSource{
        return object : AppDataSource{
            override fun getExercises(): List<Exercise> {
                return emptyList()
            }

            override fun getExerciseFilters(): Set<String> {
                return emptySet()
            }

            override fun getTrainingCenters(): List<Center> {
                return emptyList()
            }

            override fun getExpenses(): List<Expense> {
                return emptyList()
            }

            override fun getDashboardActions(): List<DashboardAction> {
                return emptyList()
            }

            override fun getBooths(): List<Booth> {
                return  emptyList()
            }

            override fun getOrders(): List<Order> {
                return emptyList()
            }

            override fun getExpensesReport(): List<ExpensesReport> {
                return emptyList()
            }

            override fun getDeliveries(): List<Delivery> {
                TODO("Not yet implemented")
            }

        }
    }
}