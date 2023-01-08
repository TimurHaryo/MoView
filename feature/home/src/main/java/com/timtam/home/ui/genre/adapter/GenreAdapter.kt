package com.timtam.home.ui.genre.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.home.ui.genre.listener.MovieGenreListener
import com.timtam.uikit.recyclerview.base.BaseListAdapter
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCacheable
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCaching
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class GenreAdapter :
    BaseListAdapter<GenreHomeItem, GenreItemViewHolder>(DIFFER),
    AttachableResource<MovieGenreListener>,
    DetachableResource,
    RecyclerHolderCacheable by RecyclerHolderCaching() {

    private var listener: MovieGenreListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreItemViewHolder = GenreItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) = with(holder) {
        setListener(listener)
        bind(getItem(position))
    }

    override fun setListener(resource: MovieGenreListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<GenreHomeItem>() {
            override fun areItemsTheSame(oldItem: GenreHomeItem, newItem: GenreHomeItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GenreHomeItem, newItem: GenreHomeItem): Boolean =
                oldItem == newItem
        }
    }
}
