package com.timtam.home.ui.genre.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.timtam.feature_item.genre.GenreItem
import com.timtam.uikit.recyclerview.base.BaseListAdapter
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCacheable
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCaching
import com.timtam.uikit.recyclerview.resourceful.AttachableResource
import com.timtam.uikit.recyclerview.resourceful.DetachableResource

class GenreAdapter :
    BaseListAdapter<GenreItem, GenreItemViewHolder>(DIFFER),
    AttachableResource<GenreListener>,
    DetachableResource,
    RecyclerHolderCacheable by RecyclerHolderCaching() {

    private var listener: GenreListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenreItemViewHolder = GenreItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) = with(holder) {
        setListener(listener)
        bind(getItem(position))
    }

    override fun setListener(resource: GenreListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<GenreItem>() {
            override fun areItemsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean =
                oldItem == newItem
        }
    }
}
