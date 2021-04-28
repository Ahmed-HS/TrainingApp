package com.app.training.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.data.model.DashboardAction
import com.app.training.databinding.DashboardActionItemBinding


class DashboardActionsAdapter(val onclick: (DashboardAction) -> Unit) : ListAdapter<DashboardAction, DashboardActionsAdapter.ActionViewHolder>(DashboardActionDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(getItem(position),onclick)
    }

    companion object {

        object DashboardActionDiffCallBack : DiffUtil.ItemCallback<DashboardAction>()
        {
            override fun areItemsTheSame(oldItem: DashboardAction, newItem: DashboardAction): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DashboardAction, newItem: DashboardAction): Boolean {
                return oldItem == newItem
            }

        }

    }


    class ActionViewHolder(val binding: DashboardActionItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : ActionViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = DashboardActionItemBinding.inflate(inflater, parent, false)
                return ActionViewHolder(itemBinding)
            }
        }



        fun bind(action: DashboardAction,onclick:(DashboardAction) -> Unit)
        {
            binding.actionCard.setOnClickListener { onclick(action) }
            binding.actionNumber.text = action.number.toString()
            binding.actionSubtitle.text = action.subtitle
            binding.subNumber.text = action.subNumber.toString()
        }
    }
}