package com.app.training.ui.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.R
import com.app.training.data.model.ExpensesReport
import com.app.training.databinding.ExpensesChartItemBinding
import com.app.training.databinding.ExpensesOverviewBinding
import com.app.training.util.MPAndroidChart.addEntriesWithLines
import com.app.training.util.MPAndroidChart.bind
import com.github.mikephil.charting.data.PieEntry

class ExpensesChartAdapter : ListAdapter<ReportsUIModel, RecyclerView.ViewHolder>(ChartDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType)
        {
            OverView_TYPE -> OverViewHolder.from(parent)
            else -> ChartViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.bind(getItem(position))
        when(val data = getItem(position))
        {
            is ReportsUIModel.OverView -> {
                (holder as OverViewHolder).bind(data.enteries)
            }

            is ReportsUIModel.ProfitAndLoss -> {
                (holder as ChartViewHolder).bind(data.expensesReport)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when(getItem(position))
        {
            is ReportsUIModel.OverView -> OverView_TYPE
            else -> Report_TYPE
        }
    }

    companion object {

        const val OverView_TYPE = Int.MIN_VALUE
        const val Report_TYPE = R.layout.expenses_chart_item

        object ChartDiffCallBack : DiffUtil.ItemCallback<ReportsUIModel>()
        {
            override fun areItemsTheSame(oldItem: ReportsUIModel, newItem: ReportsUIModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ReportsUIModel, newItem: ReportsUIModel): Boolean {
                return oldItem == newItem
            }

        }

    }

    class OverViewHolder(val binding: ExpensesOverviewBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : OverViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = ExpensesOverviewBinding.inflate(inflater, parent, false)
                return OverViewHolder(itemBinding)
            }
        }


        fun bind(expenses:List<PieEntry>)
        {
            binding.overViewChart.addEntriesWithLines {
                addAll(expenses)
            }
        }
    }

    class ChartViewHolder(val binding: ExpensesChartItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        companion object{

            fun from(parent: ViewGroup) : ChartViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = ExpensesChartItemBinding.inflate(inflater, parent, false)
                return ChartViewHolder(itemBinding)
            }
        }


        fun bind(expensesReport: ExpensesReport)
        {
            binding.expensesNumber.text = expensesReport.expenses.toString()
            binding.incomeNumber.text = expensesReport.income.toString()
            binding.pieChart.bind(expensesReport)
        }

    }

}