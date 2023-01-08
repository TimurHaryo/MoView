package com.timtam.home.ui.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.home.databinding.ItemHomeGenreBinding
import com.timtam.home.util.GenreImageGenerator.findDrawableIdByTag
import com.timtam.uikit.extension.context
import com.timtam.uikit.extension.loadImage
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class GenreItemViewHolder(
    private val binding: ItemHomeGenreBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieGenreListener>,
    DetachableResource {

    private var listener: MovieGenreListener? = null

    override fun setListener(resource: MovieGenreListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
    }

    fun bind(genre: GenreHomeItem) = with(binding) {
        mcvHomeParentGenre.setOnClickListener { listener?.onGenreClick(genre.id) }
        tvHomeGenreTitle.text = genre.type
        ivHomeGenreIcon.loadImage(
            context.findDrawableIdByTag(genre.imageTag)
        )
    }

    companion object {
        fun create(parent: ViewGroup) = GenreItemViewHolder(
            ItemHomeGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
