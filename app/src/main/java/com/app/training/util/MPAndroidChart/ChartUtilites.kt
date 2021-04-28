package com.app.training.util.MPAndroidChart

import android.graphics.Color
import android.graphics.Typeface
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import com.app.training.R
import com.app.training.data.model.ExpensesReport
import com.app.training.databinding.AnalyticsChartBinding
import com.app.training.databinding.PiechartLegendItemBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter


fun PieChart.bind(expensesReport: ExpensesReport)
{
    val entries: MutableList<PieEntry> = ArrayList()
    entries.add(PieEntry(expensesReport.income.toFloat(), ""))
    entries.add(PieEntry(expensesReport.expenses.toFloat(), ""))

    val set = PieDataSet(entries, "Expenses report").apply {
        colors = context.resources.getIntArray(R.array.incomeExpenseChartColors).toList()
        //setEntryLabelTextSize(10f)
        this.valueTextColor = this@bind.context.getColor(R.color.offWhite_200)
        this.valueTextSize = 0f
        valueFormatter = object : ValueFormatter() {
            override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                return "${DecimalFormat("#.##").format(value)}%"
            }
        }
    }

    val chartData = PieData(set)

    this.apply {
        description.isEnabled = false
        data = chartData
        this.setEntryLabelColor(this@bind.context.getColor(R.color.offWhite_200))
        legend.isEnabled = false
        this.transparentCircleRadius = 0f
        setTouchEnabled(false)
        setUsePercentValues(true)
        invalidate()
    }
}

fun PieChart.addEntriesWithLines(addEntries: MutableList<PieEntry>.() -> Unit)
{
    val entries: MutableList<PieEntry> = ArrayList()

    entries.addEntries()

    val context = this.context

    val set = PieDataSet(entries, "Expenses report").apply {

        colors = context.resources.getIntArray(R.array.pieChartColors).toList()
        //setEntryLabelTextSize(10f)
        this.valueTextColor = context.getColor(R.color.offWhite_200)
        this.valueTextSize = 12f
        valueFormatter = object : ValueFormatter() {
            override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                return "${DecimalFormat("#.##").format(value)}%"
            }
        }

        this.isUsingSliceColorAsValueLineColor = true
        this.isValueLineVariableLength = true
        valueLinePart1OffsetPercentage = 10f
        valueLinePart1Length = 1f
        valueLinePart2Length = 0.4f
        valueTextColor = Color.BLACK
        xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        //Log.d("fsadf", "addEntriesWithLines: ")
        this.selectionShift = 50f

    }

    val chartData = PieData(set)

    this.apply {
        description.isEnabled = false
        centerText = "%"
        data = chartData
        setCenterTextSize(20f)
        this.transparentCircleRadius = 0f
        this.setEntryLabelColor(Color.BLACK)
        this.setEntryLabelTextSize(12f)
        this.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
        legend.isEnabled = false
        isHighlightPerTapEnabled = false
        //setTouchEnabled(false)
        setUsePercentValues(true)

        invalidate()
    }
}


fun PieChart.addEntriesChartOnly(entries: List<PieEntry>)
{

    val context = this.context

    val set = PieDataSet(entries, "").apply {

        colors = context.resources.getIntArray(R.array.pieChartColors).toList()
        setDrawValues(false)
        valueFormatter = object : ValueFormatter() {
            override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                return "${DecimalFormat("#.##").format(value)}%"
            }

        }
    }

    val chartData = PieData(set)

    this.apply {
        description.isEnabled = false
        data = chartData
        setDrawEntryLabels(false)
        legend.isEnabled = false
        holeRadius = 75f
        transparentCircleRadius = 0f
        isHighlightPerTapEnabled = false
        //setTouchEnabled(false)
        setUsePercentValues(true)
        invalidate()
    }
}

fun AnalyticsChartBinding.bind(label:String, addEntries: MutableList<PieEntry>.() -> Unit) {

    val entries = mutableListOf<PieEntry>()
    entries.addEntries()
    val total = entries.totalSum()
    analyticsChart.addEntriesChartOnly(entries)
    val colors = analyticsChart.data.colors

    val inflater = LayoutInflater.from(this.root.context)

    chartLegend.removeAllViewsInLayout()

    moneyTotal.text = total.toString()
    moneyTotalLabel.text = label


    for (i in entries.indices)
    {
        val legendEntry = PiechartLegendItemBinding.inflate(inflater,chartLegend,true)
        legendEntry.legendLabel.text = entries[i].label
        val percentage = (entries[i].value/total) * 100
        val rounded = DecimalFormat("#.##").format(percentage)
        legendEntry.legendValue.text = "$rounded%"
        legendEntry.legendMarker.setBackgroundColor(colors[i % colors.size])

    }

}

fun Collection<PieEntry>.totalSum() : Float
{
    return this.sumByDouble { it.value.toDouble() }.toFloat()
}


