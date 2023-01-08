package com.timtam.home.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.databinding.ItemHomeSectionPlayingBinding
import com.timtam.home.ui.nowplaying.adapter.MovieNowPlayingAdapter
import com.timtam.home.ui.nowplaying.adapter.MovieNowPlayingListener
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class HomeNowPlayingViewHolder(
    private val binding: ItemHomeSectionPlayingBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieNowPlayingListener>,
    DetachableResource {

    private var listener: MovieNowPlayingListener? = null

    private var movieAdapter: MovieNowPlayingAdapter? = null

    override fun setListener(resource: MovieNowPlayingListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        movieAdapter?.releaseResource()
        movieAdapter = null
        listener = null
    }

    fun bind(data: List<MovieSnipsNowPlayingItem>) = with(binding) {
        setupRecyclerView()
        (rvHomePlaying.adapter as? MovieNowPlayingAdapter)?.submitList(data)

        tvHomePlayingSeeAll.setOnClickListener { listener?.onMoreClick() }
    }

    fun showMovie(data: List<MovieSnipsNowPlayingItem>) = with(binding.rvHomePlaying) {
        setupRecyclerView()
        (adapter as? MovieNowPlayingAdapter)?.submitList(data)
    }

    fun showErrorMovie() {
        e { "NOW PLAYING ITEM ERROR" }
    }

    fun showEmptyMovie() {
        e { "NOW PLAYING ITEM EMPTY" }
    }

    private fun setupRecyclerView() = with(binding.rvHomePlaying) {
        preAttach {
            movieAdapter = MovieNowPlayingAdapter().apply {
                setListener(listener)
            }
            adapter = movieAdapter
        }
    }

    companion object {
        fun create(parent: ViewGroup) = HomeNowPlayingViewHolder(
            ItemHomeSectionPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
