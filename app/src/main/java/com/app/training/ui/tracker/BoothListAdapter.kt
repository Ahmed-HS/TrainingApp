package com.app.training.ui.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.training.data.model.Booth
import com.app.training.databinding.BoothListItemBinding

class BoothListAdapter : ListAdapter<Booth,BoothListAdapter.BoothViewHolder>(BoothDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoothViewHolder {
        return BoothViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BoothViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {

        object BoothDiffCallBack : DiffUtil.ItemCallback<Booth>()
        {
            override fun areItemsTheSame(oldItem: Booth, newItem: Booth): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Booth, newItem: Booth): Boolean {
                return oldItem == newItem
            }

        }

    }

    class BoothViewHolder(val binding: BoothListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {

        companion object{

            fun from(parent:ViewGroup) : BoothViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = BoothListItemBinding.inflate(inflater, parent, false)
                return BoothViewHolder(itemBinding)
            }
        }


        fun bind(booth: Booth)
        {
            binding.title.text = booth.name
            binding.subtitle.text = booth.description
            binding.number.text = booth.number.toString()
            binding.progressIndicator.max = booth.maxProgress
            binding.progressIndicator.progress = booth.currentProgress
            binding.currentProgress.text = booth.currentProgress.toString()
            binding.maxProgress.text = booth.maxProgress.toString()

            val photoImageView = binding.photo

            Glide.with(photoImageView)
                .load(booth.imgURL)
                .circleCrop()
                .into(photoImageView)
        }

    }

}