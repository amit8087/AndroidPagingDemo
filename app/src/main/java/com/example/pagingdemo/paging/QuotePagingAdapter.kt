package com.example.pagingdemo.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingdemo.R
import com.example.pagingdemo.databinding.QuoteItemBinding
import com.example.pagingdemo.models.Result

class QuotePagingAdapter() :
    PagingDataAdapter<Result, QuotePagingAdapter.QuoteViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = QuoteItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteViewHolder(binding.root)

//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.quote_item, parent, false)
//        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            holder.quote?.text = item.content
    }

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quote: TextView? = itemView.findViewById(R.id.tvQuote)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}