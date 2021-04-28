package com.app.training.ui.spendingAnalytics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.data.model.Store
import com.app.training.databinding.StoreListItemBinding


class StoreListAdapter() :
        ListAdapter<Store, StoreListAdapter.StoreViewHolder>(StoreDiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        object StoreDiffCallBack : DiffUtil.ItemCallback<Store>()
        {
            override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem == newItem
            }

        }

    }


    class StoreViewHolder(val binding: StoreListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : StoreViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = StoreListItemBinding.inflate(inflater, parent, false)
                return StoreViewHolder(itemBinding)
            }
        }



        fun bind(store: Store)
        {
            binding.storeName.text = store.name
            binding.moneySpent.text = store.amountSpent.toString()
        }
    }


}