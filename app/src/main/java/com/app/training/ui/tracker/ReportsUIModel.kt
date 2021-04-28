package com.app.training.ui.tracker

import com.app.training.data.model.ExpensesReport
import com.github.mikephil.charting.data.PieEntry

sealed class ReportsUIModel {

    data class OverView(val enteries: List<PieEntry>) : ReportsUIModel()

    data class ProfitAndLoss(val expensesReport: ExpensesReport) : ReportsUIModel()

}