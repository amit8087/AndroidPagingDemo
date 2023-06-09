package com.example.pagingdemo.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingdemo.R

class LoaderAdapter: LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val progressBar: ProgressBar? = itemView.findViewById(R.id.pbLoadingQuotes)
        fun bind(loadState: LoadState){
            progressBar?.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.loader_item, parent,false)
        return LoaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}