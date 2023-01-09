package com.timtam.home.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.databinding.ItemHomeSectionPlayingBinding
import com.timtam.home.databinding.LottieHomeItemLoadingBinding
import com.timtam.home.ui.nowplaying.adapter.MovieNowPlayingAdapter
import com.timtam.home.ui.nowplaying.argument.HomeMovieNowPlayingArg
import com.timtam.home.ui.nowplaying.listener.MovieNowPlayingListener
import com.timtam.uikit.extension.bindingStubType
import com.timtam.uikit.extension.gone
import com.timtam.uikit.extension.inflateIf
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.extension.visible
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

    private var argument: HomeMovieNowPlayingArg = HomeMovieNowPlayingArg()

    override fun setListener(resource: MovieNowPlayingListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        movieAdapter?.releaseResource()
        movieAdapter = null
        listener = null
    }

    fun setArgument(arg: HomeMovieNowPlayingArg) {
        this.argument = arg
    }

    fun bind(data: List<MovieSnipsNowPlayingItem>) = with(binding) {
        setLoading(argument.isLoading)
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

    fun setLoading(isLoading: Boolean) =
        binding.vsHomePlayingLoading.inflateIf(isLoading) {
            if (isLoading) {
                startLoading()
            } else {
                stopLoading()
            }
        }

    private fun startLoading() =
        bindingStubType<LottieHomeItemLoadingBinding>(binding.vsHomePlayingLoading) {
            lavHomeItemLoading.playAnimation()
            root.visible()
        }

    private fun stopLoading() =
        bindingStubType<LottieHomeItemLoadingBinding>(binding.vsHomePlayingLoading) {
            lavHomeItemLoading.pauseAnimation()
            root.gone()
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
