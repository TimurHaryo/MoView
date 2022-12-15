package com.timtam.home.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.R
import com.timtam.home.ui.home.adapter.genre.HomeGenreViewHolder
import com.timtam.home.ui.home.adapter.header.HomeHeaderViewHolder
import com.timtam.home.ui.home.adapter.nowplaying.HomeNowPlayingViewHolder
import com.timtam.home.ui.home.adapter.toprated.HomeTopRatedViewHolder
import com.timtam.home.ui.model.HomeViewType

class HomeAdapter : ListAdapter<HomeViewType, RecyclerView.ViewHolder>(DIFFER) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            HomeViewType.HEADER -> R.layout.item_home_section_header
            HomeViewType.GENRE -> R.layout.item_home_section_genre
            HomeViewType.NOW_PLAYING -> R.layout.item_home_section_playing
            HomeViewType.TOP_RATED -> R.layout.item_home_section_top_rated
            else -> RecyclerView.NO_POSITION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_home_section_header -> HomeHeaderViewHolder.create(parent)
            R.layout.item_home_section_genre -> HomeGenreViewHolder.create(parent)
            R.layout.item_home_section_playing -> HomeNowPlayingViewHolder.create(parent)
            R.layout.item_home_section_top_rated -> HomeTopRatedViewHolder.create(parent)
            else -> throw IllegalStateException("Invalid view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = Unit

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<HomeViewType>() {
            override fun areItemsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem == newItem
        }
    }
}
