package com.timtam.home.ui.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.common_android.extension.i
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.databinding.ItemHomeSectionTopRatedBinding
import com.timtam.home.ui.toprated.adapter.MovieTopRatedAdapter
import com.timtam.home.ui.toprated.listener.MovieTopRatedListener
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource

class HomeTopRatedViewHolder(
    private val binding: ItemHomeSectionTopRatedBinding
) : RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieTopRatedListener>,
    DetachableResource {

    private var listener: MovieTopRatedListener? = null

    private var movieAdapter: MovieTopRatedAdapter? = null

    override fun setListener(resource: MovieTopRatedListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        movieAdapter?.releaseResource()
        movieAdapter = null
        listener = null
    }

    fun bind(data: List<MovieSnipsTopRatedItem>) = with(binding) {
        setupRecyclerView()
        i { "TIMUR bindTopRated" }
        (rvHomeTopRated.adapter as? MovieTopRatedAdapter)?.submitList(data)

        tvHomeTopRatedSeeAll.setOnClickListener { listener?.onMoreClick() }
    }

    fun showMovie(data: List<MovieSnipsTopRatedItem>) = with(binding.rvHomeTopRated) {
        setupRecyclerView()
        i { "TIMUR showTopRated" }
        (adapter as? MovieTopRatedAdapter)?.submitList(data)
    }

    fun showErrorMovie() {
        e { "TOP RATED ITEM ERROR" }
    }

    fun showEmptyMovie() {
        e { "TOP RATED ITEM EMPTY" }
    }

    private fun setupRecyclerView() = with(binding.rvHomeTopRated) {
        preAttach {
            movieAdapter = MovieTopRatedAdapter().apply {
                setListener(listener)
            }
            adapter = movieAdapter
        }
    }

    companion object {
        fun create(parent: ViewGroup) = HomeTopRatedViewHolder(
            ItemHomeSectionTopRatedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
