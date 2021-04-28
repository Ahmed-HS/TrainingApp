package com.app.training.ui.spendingAnalytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.data.model.Spending
import com.app.training.databinding.SpendingCardBinding

class SpendingListAdapter() :
        ListAdapter<Spending, SpendingListAdapter.SpendingViewHolder>(SpendingDiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
        return SpendingViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SpendingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        object SpendingDiffCallBack : DiffUtil.ItemCallback<Spending>()
        {
            override fun areItemsTheSame(oldItem: Spending, newItem: Spending,): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Spending, newItem: Spending,): Boolean {
                return oldItem == newItem
            }

        }

    }


    class SpendingViewHolder(val binding: SpendingCardBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : SpendingViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = SpendingCardBinding.inflate(inflater, parent, false)
                return SpendingViewHolder(itemBinding)
            }
        }



        fun bind(spending: Spending)
        {
            binding.categoryName.text = spending.name
            binding.paymentCount.text = spending.paymentsNumber.toString()
            binding.amountSpent.text = spending.amountSpent.toString()
        }
    }


}