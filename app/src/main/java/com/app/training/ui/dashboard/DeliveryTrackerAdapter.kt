package com.app.training.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.data.model.Delivery
import com.app.training.databinding.OrderTrackerCardBinding
import com.app.training.util.loadURL

interface DeliveryClickListener
{
    fun onClick(delivery: Delivery)

    fun onCall(delivery: Delivery)
}

class DeliveryTrackerAdapter(val clickListener: DeliveryClickListener) :
        ListAdapter<Delivery, DeliveryTrackerAdapter.OrderViewHolder>(DeliveryDiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    companion object {

        object DeliveryDiffCallBack : DiffUtil.ItemCallback<Delivery>()
        {
            override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
                return oldItem == newItem
            }

        }

    }


    class OrderViewHolder(val binding: OrderTrackerCardBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : OrderViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = OrderTrackerCardBinding.inflate(inflater, parent, false)
                return OrderViewHolder(itemBinding)
            }
        }



        fun bind(delivery: Delivery, clickListener: DeliveryClickListener)
        {
            binding.orderTitle.text = delivery.name
            binding.orderDescription.text = delivery.description
            binding.callButton.setOnClickListener { clickListener.onCall(delivery) }
            binding.orderImage.loadURL(delivery.imgURL)
        }
    }


}