package com.timtam.home.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.R
import com.timtam.home.type.HomeViewType
import com.timtam.home.ui.genre.HomeGenreViewHolder
import com.timtam.home.ui.genre.adapter.MovieGenreListener
import com.timtam.home.ui.genre.payload.HomeGenrePayload
import com.timtam.home.ui.header.HomeHeaderViewHolder
import com.timtam.home.ui.header.listener.HomeHeaderListener
import com.timtam.home.ui.header.payload.HomeHeaderPayload
import com.timtam.home.ui.home.adapter.payload.HomePayload
import com.timtam.home.ui.nowplaying.HomeNowPlayingViewHolder
import com.timtam.home.ui.nowplaying.adapter.MovieNowPlayingListener
import com.timtam.home.ui.nowplaying.payload.HomeMovieNowPlayingPayload
import com.timtam.home.ui.toprated.HomeTopRatedViewHolder
import com.timtam.home.ui.toprated.adapter.MovieTopRatedListener
import com.timtam.home.ui.toprated.payload.HomeMovieTopRatedPayload
import com.timtam.uikit.extension.payloadByType
import com.timtam.uikit.recyclerview.base.BaseListAdapter
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCacheable
import com.timtam.uikit.recyclerview.cachecontrol.RecyclerHolderCaching
import com.timtam.uikit.recyclerview.payloadcontrol.AdapterPayloadDelegate
import com.timtam.uikit.recyclerview.payloadcontrol.AdapterPayloadDelegateImpl
import com.timtam.uikit.recyclerview.resource.AdapterArgument
import com.timtam.uikit.recyclerview.resource.DetachableResource

class HomeAdapter :
    BaseListAdapter<HomeViewType, RecyclerView.ViewHolder>(DIFFER),
    AdapterArgument<HomeAdapter.Arg>,
    DetachableResource,
    AdapterPayloadDelegate<HomePayload> by AdapterPayloadDelegateImpl(),
    RecyclerHolderCacheable by RecyclerHolderCaching() {

    override var argument: Arg = Arg()

    private var homeHeaderListener: HomeHeaderListener? = null
    private var movieGenreListener: MovieGenreListener? = null
    private var movieNowPlayingListener: MovieNowPlayingListener? = null
    private var movieTopRatedListener: MovieTopRatedListener? = null

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            HomeViewType.HEADER -> R.layout.item_home_section_header
            HomeViewType.GENRE -> R.layout.item_home_section_genre
            HomeViewType.NOW_PLAYING -> R.layout.item_home_section_playing
            HomeViewType.TOP_RATED -> R.layout.item_home_section_top_rated
            else -> RecyclerView.NO_POSITION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_home_section_header -> HomeHeaderViewHolder.create(parent)
            R.layout.item_home_section_genre -> HomeGenreViewHolder.create(parent)
            R.layout.item_home_section_playing -> HomeNowPlayingViewHolder.create(parent)
            R.layout.item_home_section_top_rated -> HomeTopRatedViewHolder.create(parent)
            else -> throw IllegalStateException("Invalid view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        addValidHolderPosition(position)
        when (holder) {
            is HomeHeaderViewHolder -> with(holder) {
                setListener(homeHeaderListener)
                bind()
            }
            is HomeGenreViewHolder -> with(holder) {
                setListener(movieGenreListener)
                bind(argument.genreArgs)
            }
            is HomeNowPlayingViewHolder -> with(holder) {
                setListener(movieNowPlayingListener)
                bind(argument.nowPlayingArgs)
            }
            is HomeTopRatedViewHolder -> with(holder) {
                setListener(movieTopRatedListener)
                bind(argument.topRatedArgs)
            }
            else -> Unit
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        payloadByType<HomeHeaderPayload>(
            payloads = payloads,
            onPayload = { payload ->
                (holder as? HomeHeaderViewHolder)?.apply {
                    setListener(homeHeaderListener)
                    when (payload) {
                        is HomeHeaderPayload.ShowMantra -> showMantra(payload.mantra)
                    }
                }
            }
        )

        payloadByType<HomeGenrePayload>(
            payloads = payloads,
            onPayload = { payload ->
                (holder as? HomeGenreViewHolder)?.apply {
                    setListener(movieGenreListener)
                    when (payload) {
                        is HomeGenrePayload.ShowData -> showGenre(payload.genres)
                        is HomeGenrePayload.ShowError -> showErrorGenre()
                    }
                }
            }
        )

        payloadByType<HomeMovieNowPlayingPayload>(
            payloads = payloads,
            onPayload = { payload ->
                (holder as? HomeNowPlayingViewHolder)?.apply {
                    setListener(movieNowPlayingListener)
                    when (payload) {
                        is HomeMovieNowPlayingPayload.ShowData -> showMovie(payload.movies)
                        is HomeMovieNowPlayingPayload.ShowEmpty -> showEmptyMovie()
                        is HomeMovieNowPlayingPayload.ShowError -> showErrorMovie()
                    }
                }
            }
        )

        payloadByType<HomeMovieTopRatedPayload>(
            payloads = payloads,
            onPayload = { payload ->
                (holder as? HomeTopRatedViewHolder)?.apply {
                    setListener(movieTopRatedListener)
                    when (payload) {
                        is HomeMovieTopRatedPayload.ShowData -> showMovie(payload.movies)
                        is HomeMovieTopRatedPayload.ShowEmpty -> showEmptyMovie()
                        is HomeMovieTopRatedPayload.ShowError -> showErrorMovie()
                    }
                }
            }
        )
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        executePendingPayload(holder.absoluteAdapterPosition)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        registerHost(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        cleanUp()
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        removeValidHolderPosition(holder.absoluteAdapterPosition)
    }

    override fun releaseResource() {
        homeHeaderListener = null
        movieGenreListener = null
        movieNowPlayingListener = null
        movieTopRatedListener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    fun registerListener(
        homeHeaderListener: HomeHeaderListener?,
        movieGenreListener: MovieGenreListener?,
        movieNowPlayingListener: MovieNowPlayingListener?,
        movieTopRatedListener: MovieTopRatedListener?
    ) {
        this.homeHeaderListener = homeHeaderListener
        this.movieGenreListener = movieGenreListener
        this.movieNowPlayingListener = movieNowPlayingListener
        this.movieTopRatedListener = movieTopRatedListener
    }

    data class Arg(
        var genreArgs: List<GenreHomeItem> = emptyList(),
        var nowPlayingArgs: List<MovieSnipsNowPlayingItem> = emptyList(),
        var topRatedArgs: List<MovieSnipsTopRatedItem> = emptyList()
    )

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<HomeViewType>() {
            override fun areItemsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem == newItem
        }
    }
}
