package com.timtam.home.ui.nowplaying.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_kotlin.extension.orZero
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.databinding.ItemHomeNowPlayingBinding
import com.timtam.uikit.extension.context
import com.timtam.uikit.extension.dpToPx
import com.timtam.uikit.extension.loadImageWithRadius
import com.timtam.uikit.recyclerview.resourceful.AttachableResource
import com.timtam.uikit.recyclerview.resourceful.DetachableResource
import kotlin.math.roundToInt

class MovieNowPlayingItemViewHolder(
    private val binding: ItemHomeNowPlayingBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieNowPlayingListener>,
    DetachableResource {

    private var listener: MovieNowPlayingListener? = null

    override fun setListener(resource: MovieNowPlayingListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
    }

    fun bind(movie: MovieSnipsNowPlayingItem) = with(binding) {
        isAdultContent = movie.isAdult
        mcvHomeParentNowPlaying.setOnClickListener { listener?.onMovieClick(movie.id) }

        ivHomeNowPlayingBackdrop.loadImageWithRadius(
            url = movie.backdropPath,
            radius = context?.dpToPx(BACKDROP_RADIUS)?.roundToInt().orZero(),
            usePlaceHolder = false
        )

        tvHomeNowPlayingTitle.text = movie.title
        tvHomeNowPlayingGenres.text = movie.genreGroup
        tvHomeNowPlayingRelease.text = movie.releaseDate
        tvHomeNowPlayingRating.text = movie.rating.toString()
    }

    companion object {
        private const val BACKDROP_RADIUS = 8

        fun create(parent: ViewGroup) = MovieNowPlayingItemViewHolder(
            ItemHomeNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
