package com.app.training.ui.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.training.data.model.Order
import com.app.training.databinding.OrderListItemBinding

class OrderListAdapter : ListAdapter<Order,OrderListAdapter.OrderViewHolder>(OrderDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {

        object OrderDiffCallBack : DiffUtil.ItemCallback<Order>()
        {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

        }

    }

    class OrderViewHolder(val binding: OrderListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        companion object{

            fun from(parent:ViewGroup) : OrderViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = OrderListItemBinding.inflate(inflater, parent, false)
                return OrderViewHolder(itemBinding)
            }
        }


        fun bind(order: Order)
        {
            binding.orderName.text = order.name
            binding.orderQuantity.text = order.quantity.toString()

            val photoImageView = binding.orderPhoto

            Glide.with(photoImageView)
                .load(order.imgURL)
                .circleCrop()
                .into(photoImageView)
        }

    }

}