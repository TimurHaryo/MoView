package com.timtam.home.ui.toprated.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_kotlin.extension.orZero
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.databinding.ItemHomeTopRatedBinding
import com.timtam.uikit.extension.context
import com.timtam.uikit.extension.dpToPx
import com.timtam.uikit.extension.loadImageWithRadius
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource
import kotlin.math.roundToInt

class MovieTopRatedItemViewHolder(
    private val binding: ItemHomeTopRatedBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieTopRatedListener>,
    DetachableResource {

    private var listener: MovieTopRatedListener? = null

    override fun setListener(resource: MovieTopRatedListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
    }

    fun bind(movie: MovieSnipsTopRatedItem) = with(binding) {
        clHomeParentTopRated.setOnClickListener { listener?.onMovieClick(movie.id) }

        ivHomeTopRatedPoster.loadImageWithRadius(
            url = movie.posterPath,
            radius = context?.dpToPx(POSTER_RADIUS)?.roundToInt().orZero(),
            usePlaceHolder = false
        )

        tvHomeTopRatedRating.text = movie.rating.toString()
        tvHomeTopRatedTitle.text = movie.title
    }

    companion object {
        private const val POSTER_RADIUS = 8

        fun create(parent: ViewGroup) = MovieTopRatedItemViewHolder(
            ItemHomeTopRatedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
