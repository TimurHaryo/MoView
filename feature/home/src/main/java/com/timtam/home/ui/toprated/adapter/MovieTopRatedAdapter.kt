package com.timtam.home.ui.toprated.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.uikit.recyclerview.base.BaseListAdapter
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCacheable
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCaching
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class MovieTopRatedAdapter :
    BaseListAdapter<MovieSnipsTopRatedItem, MovieTopRatedItemViewHolder>(DIFFER),
    AttachableResource<MovieTopRatedListener>,
    DetachableResource,
    RecyclerHolderCacheable by RecyclerHolderCaching() {

    private var listener: MovieTopRatedListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieTopRatedItemViewHolder = MovieTopRatedItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: MovieTopRatedItemViewHolder, position: Int) =
        with(holder) {
            setListener(listener)
            bind(getItem(position))
        }

    override fun setListener(resource: MovieTopRatedListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<MovieSnipsTopRatedItem>() {
            override fun areItemsTheSame(
                oldItem: MovieSnipsTopRatedItem,
                newItem: MovieSnipsTopRatedItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MovieSnipsTopRatedItem,
                newItem: MovieSnipsTopRatedItem
            ): Boolean =
                oldItem == newItem
        }
    }
}
