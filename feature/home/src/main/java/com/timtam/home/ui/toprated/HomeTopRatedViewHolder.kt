package com.timtam.home.ui.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.databinding.ItemHomeSectionTopRatedBinding
import com.timtam.home.ui.toprated.adapter.MovieTopRatedAdapter
import com.timtam.home.ui.toprated.adapter.MovieTopRatedListener
import com.timtam.uikit.extension.detachFromAdapter
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.recyclerview.resourceful.AttachableResource
import com.timtam.uikit.recyclerview.resourceful.DetachableResource

class HomeTopRatedViewHolder(
    private val binding: ItemHomeSectionTopRatedBinding
) : RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieTopRatedListener>,
    DetachableResource {

    private var listener: MovieTopRatedListener? = null

    private val movies: ArrayList<MovieSnipsTopRatedItem> = arrayListOf()

    private var movieAdapter: MovieTopRatedAdapter? = null

    override fun setListener(resource: MovieTopRatedListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        movieAdapter?.releaseResource()
        movieAdapter = null
        listener = null
        binding.rvHomeTopRated.detachFromAdapter()
    }

    fun bind() = with(binding) {
        rvHomeTopRated.preAttach {
            movieAdapter = MovieTopRatedAdapter().apply {
                setListener(listener)
            }
            adapter = movieAdapter
        }

        tvHomeTopRatedSeeAll.setOnClickListener { listener?.onMoreClick() }
    }

    fun showMovie(data: List<MovieSnipsTopRatedItem>) {
        movies.clear()
        movies.addAll(data)

        movieAdapter?.submitList(movies)
    }

    fun showErrorMovie() {
        e { "TOP RATED ITEM ERROR" }
    }

    fun showEmptyMovie() {
        e { "TOP RATED ITEM EMPTY" }
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
