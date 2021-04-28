package com.app.training.ui.exercise

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.training.R
import com.app.training.databinding.ExserciseListItemBinding
import com.app.training.databinding.TextSeperatorBinding
import com.app.training.data.model.Exercise


interface ExerciseClickListener
{
    fun onShare(exercise: Exercise)

    fun onJoin(exercise: Exercise)

    fun onPhotoClick(exercise: Exercise)
}

class ExerciseListAdapter(private val clickListener: ExerciseClickListener) :  ListAdapter<ExerciseUIModel, RecyclerView.ViewHolder>(EventDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType)
        {
            EVENT_TYPE -> EventViewHolder.from(parent)
            else -> SeperatorViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is EventViewHolder -> {
                val item = getItem(position) as ExerciseUIModel.ExerciseItem
                holder.bind(item,clickListener)
            }
            is SeperatorViewHolder -> {
                val item = getItem(position) as ExerciseUIModel.TimeSeparator
                holder.bind(item)
            }
        }
        
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position))
        {
            is ExerciseUIModel.ExerciseItem -> EVENT_TYPE
            else -> SEPARATOR_TYPE
        }
    }

    companion object
    {
        const val SEPARATOR_TYPE = Int.MIN_VALUE
        const val EVENT_TYPE = R.layout.exsercise_list_item

        object EventDiffCallBack : DiffUtil.ItemCallback<ExerciseUIModel>()
        {
            override fun areItemsTheSame(oldItem: ExerciseUIModel, newItem: ExerciseUIModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ExerciseUIModel, newItem: ExerciseUIModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    class SeperatorViewHolder(val binding: TextSeperatorBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup) : RecyclerView.ViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = TextSeperatorBinding.inflate(inflater,parent,false)
                return SeperatorViewHolder(itemBinding)
            }

        }

        fun bind(item: ExerciseUIModel.TimeSeparator)
        {
            binding.separator.text = item.time
        }
    }

    class EventViewHolder(val binding: ExserciseListItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        companion object{

            fun from(parent: ViewGroup,) : RecyclerView.ViewHolder
            {
                val inflater = LayoutInflater.from(parent.context)
                val itemBinding = ExserciseListItemBinding.inflate(inflater, parent, false)
                return EventViewHolder(itemBinding)
            }

        }

        fun bind(item: ExerciseUIModel.ExerciseItem,clickListener:ExerciseClickListener)
        {
            val exercise = item.exercise
            binding.title.text = exercise.title
            binding.subtitle.text = exercise.description
            binding.difficulty.text = exercise.address

            binding.shareBtn.setOnClickListener {
                clickListener.onShare(exercise)
            }

            binding.joinBtn.setOnClickListener {
                clickListener.onJoin(exercise)
            }

            binding.photo.setOnClickListener {
                clickListener.onPhotoClick(exercise)
            }

            Glide.with(binding.photo.context)
                    .load(exercise.imgURL)
                    .circleCrop()
                    .into(binding.photo)

            val duration = exercise.duration
            val durationText = if(duration.toHours() != 0L){
                duration.toHours().toString() + "Hr"
            }
            else
            {
                duration.toMinutes().toString() + " Min"
            }

            binding.time.text = durationText
        }
    }

}