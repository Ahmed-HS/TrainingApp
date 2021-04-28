package com.app.training.ui.popupList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.training.R
import com.app.training.databinding.TextListItemBinding

data class PopupListItem(
        val title : String,
        val subtitle : String,
)


class PopUpListAdapter(var selectedPos:Int,val onClick: (PopupListItem,selected:Int) -> Unit)
    : ListAdapter<PopupListItem, PopUpListAdapter.ItemViewHolder>(ItemDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position)){
            onClick(getItem(position),position)
            notifyItemChanged(selectedPos)
            selectedPos = position
            notifyItemChanged(selectedPos)
        }
        holder.setSelected(selectedPos == position)
    }


    companion object {

        object ItemDiffCallBack : DiffUtil.ItemCallback<PopupListItem>()
        {
            override fun areItemsTheSame(oldItem: PopupListItem, newItem: PopupListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PopupListItem, newItem: PopupListItem): Boolean {
                return oldItem == newItem
            }

        }

    }

    class ItemViewHolder(val binding: TextListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : ItemViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = TextListItemBinding.inflate(inflater, parent, false)
                return ItemViewHolder(itemBinding)
            }
        }



        fun bind(item: PopupListItem, onClick: (PopupListItem) -> Unit)
        {
            binding.itemTitle.text = item.title
            binding.itemSubtitle.text = item.subtitle
            binding.listItem.setOnClickListener {
                onClick(item)
            }
        }

        fun setSelected(isSelected:Boolean)
        {
            val context = itemView.context
            if(isSelected)
            {
                binding.itemTitle.setTextColor(context.getColor(R.color.accentDarkPink))
                binding.itemSubtitle.setTextColor(context.getColor(R.color.accentDarkPink))
                binding.materialCardView.visibility = View.VISIBLE
            }
            else
            {
                binding.itemTitle.setTextColor(context.getColor(R.color.chipSelectedLight))
                binding.itemSubtitle.setTextColor(context.getColor(R.color.chipSelectedLight))
                binding.materialCardView.visibility = View.INVISIBLE
            }
        }
    }

}