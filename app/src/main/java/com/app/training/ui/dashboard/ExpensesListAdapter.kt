package com.app.training.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.data.model.Expense
import com.app.training.databinding.ExpensesListItemBinding
import java.time.format.DateTimeFormatter

interface ExpensesClickListener
{
    fun onClick(expense: Expense)

    fun onCall(expense: Expense)
}

class ExpensesListAdapter(val expensesClickListener: ExpensesClickListener) :
        ListAdapter<Expense, ExpensesListAdapter.ExpenseViewHolder>(ExpenseDiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position),expensesClickListener)
    }

    companion object {

        object ExpenseDiffCallBack : DiffUtil.ItemCallback<Expense>()
        {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }

        }

    }


    class ExpenseViewHolder(val binding: ExpensesListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : ExpenseViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = ExpensesListItemBinding.inflate(inflater, parent, false)
                return ExpenseViewHolder(itemBinding)
            }
        }



        fun bind(expense: Expense, expensesClickListener: ExpensesClickListener)
        {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
            binding.expenseTitle.text = expense.title
            binding.expenseTime.text = formatter.format(expense.date)
            binding.phoneNumber.text = expense.phoneNumber
            binding.callButton.setOnClickListener { expensesClickListener.onCall(expense) }
        }
    }


}