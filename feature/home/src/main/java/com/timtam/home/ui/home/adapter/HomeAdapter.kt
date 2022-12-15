package com.timtam.home.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.ui.model.HomeViewType

class HomeAdapter : ListAdapter<HomeViewType, RecyclerView.ViewHolder>(DIFFER) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<HomeViewType>() {
            override fun areItemsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem == newItem
        }
    }
}
