package com.app.training.ui.spendingAnalytics

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.training.R
import com.app.training.util.getColorFromAttr



class MonthPagerAdapter
    : ListAdapter<String, MonthPagerAdapter.TextViewHolder>(StringDiffCallBack) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        return TextViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {

        object StringDiffCallBack : DiffUtil.ItemCallback<String>()
        {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }

    }

    class TextViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    {
        companion object{

            fun from(parent: ViewGroup) : TextViewHolder
            {
                val newtextView =TextView(parent.context).apply {
                    gravity = Gravity.CENTER
                    typeface = Typeface.DEFAULT_BOLD
                    val color = context.getColorFromAttr(R.attr.textOnSurfaceDark)
                    setTextColor(color)
                    layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT)
                }
                return TextViewHolder(newtextView)
            }
        }


        fun bind(month: String)
        {
            textView.text = month
        }
    }
}

fun ViewPager2.nextPage()
{
    val currentIndex = currentItem
    val size = this.adapter?.itemCount!!
    val nextIndex = currentIndex + 1
    if(nextIndex < size)
    {
        currentItem = nextIndex
    }
}

fun ViewPager2.previousPage()
{
    val currentIndex = currentItem

    val previousIndex = currentIndex - 1
    if(previousIndex >= 0)
    {
        currentItem = previousIndex
    }
}

