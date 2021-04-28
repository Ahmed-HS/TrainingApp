package com.app.training.ui.tracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.training.data.model.Booth
import com.app.training.data.model.ExpensesReport
import com.app.training.data.model.Order
import com.app.training.data.sources.AppDataSource
import com.app.training.di.FakeDataSource
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TrackerViewModel @Inject constructor(
        @FakeDataSource private val dataSource: AppDataSource
) : ViewModel() {

    private val mBoothList = MutableLiveData<List<Booth>>()
    val boothList:LiveData<List<Booth>> = mBoothList


    private val mOrdersList = MutableLiveData<List<Order>>()
    val ordersList:LiveData<List<Order>> = mOrdersList



    private val mReportsList = MutableLiveData<List<ExpensesReport>>()
    val reportsList:LiveData<List<ReportsUIModel>> = Transformations.map(mReportsList){
        val uiModel = mutableListOf<ReportsUIModel>()

        val pieEnteries = mutableListOf<PieEntry>().apply {
            add(PieEntry(32f, "Housing"))
            add(PieEntry(100f, "Food & Drinks"))
            add(PieEntry(55f, "Shopping"))
            add(PieEntry(75f, "Transportation"))
            add(PieEntry(120f, "Entertainment"))
            add(PieEntry(90f, "Bills"))
        }

        val overview = ReportsUIModel.OverView(pieEnteries)

        val toUIModel = it.map { ReportsUIModel.ProfitAndLoss(it) }

        uiModel.apply {
            add(overview)
            addAll(toUIModel)
        }

    }



    init {

        mBoothList.value = dataSource.getBooths()
        mOrdersList.value = dataSource.getOrders()
        mReportsList.value = dataSource.getExpensesReport()
    }

}