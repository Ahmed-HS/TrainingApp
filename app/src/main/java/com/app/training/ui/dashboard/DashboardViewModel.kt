package com.app.training.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.training.data.model.DashboardAction
import com.app.training.data.model.Delivery
import com.app.training.data.model.Expense
import com.app.training.data.sources.AppDataSource
import com.app.training.di.FakeDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(@FakeDataSource private val datasource: AppDataSource)
    : ViewModel()
{
    private val mDashboardActions = MutableLiveData<List<DashboardAction>>()
    val dashboardActions:LiveData<List<DashboardAction>> = mDashboardActions

    private val mExpenses = MutableLiveData<List<Expense>>()
    val expenses:LiveData<List<Expense>> = mExpenses


    private val mDeliveries = MutableLiveData<List<Delivery>>()
    val deliveries:LiveData<List<Delivery>> = mDeliveries


    init {
        mDashboardActions.value = datasource.getDashboardActions()
        mExpenses.value = datasource.getExpenses()
        mDeliveries.value = datasource.getDeliveries()
    }
}