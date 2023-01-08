package com.timtam.home.ui.nowplaying.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.ui.nowplaying.listener.MovieNowPlayingListener
import com.timtam.uikit.recyclerview.base.BaseListAdapter
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCacheable
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCaching
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class MovieNowPlayingAdapter :
    BaseListAdapter<MovieSnipsNowPlayingItem, MovieNowPlayingItemViewHolder>(DIFFER),
    AttachableResource<MovieNowPlayingListener>,
    DetachableResource,
    RecyclerHolderCacheable by RecyclerHolderCaching() {

    private var listener: MovieNowPlayingListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieNowPlayingItemViewHolder = MovieNowPlayingItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: MovieNowPlayingItemViewHolder, position: Int) =
        with(holder) {
            setListener(listener)
            bind(getItem(position))
        }

    override fun setListener(resource: MovieNowPlayingListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<MovieSnipsNowPlayingItem>() {
            override fun areItemsTheSame(
                oldItem: MovieSnipsNowPlayingItem,
                newItem: MovieSnipsNowPlayingItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieSnipsNowPlayingItem,
                newItem: MovieSnipsNowPlayingItem
            ): Boolean =
                oldItem == newItem
        }
    }
}
