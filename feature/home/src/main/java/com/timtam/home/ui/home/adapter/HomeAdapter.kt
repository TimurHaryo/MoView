package com.timtam.home.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.R
import com.timtam.home.type.HomeViewType
import com.timtam.home.ui.genre.HomeGenreViewHolder
import com.timtam.home.ui.genre.adapter.MovieGenreListener
import com.timtam.home.ui.genre.payload.HomeGenrePayload
import com.timtam.home.ui.header.HomeHeaderViewHolder
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
import com.timtam.uikit.recyclerview.payloadcontrol.AdapterPayloadExecution
import com.timtam.uikit.recyclerview.payloadcontrol.AdapterPayloadExecutor
import com.timtam.uikit.recyclerview.resourceful.DetachableResource

class HomeAdapter :
    BaseListAdapter<HomeViewType, RecyclerView.ViewHolder>(DIFFER),
    DetachableResource,
    AdapterPayloadExecution<HomePayload> by AdapterPayloadExecutor(),
    RecyclerHolderCacheable by RecyclerHolderCaching() {

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
        when (holder) {
            is HomeGenreViewHolder -> with(holder) {
                setListener(movieGenreListener)
                bind()
            }
            is HomeNowPlayingViewHolder -> with(holder) {
                setListener(movieNowPlayingListener)
                bind()
            }
            is HomeTopRatedViewHolder -> with(holder) {
                setListener(movieTopRatedListener)
                bind()
            }
            else -> Unit
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        payloadByType<HomeGenrePayload>(
            payloads = payloads,
            emptyPayload = { onBindViewHolder(holder, position) },
            onPayload = { payload ->
                when (payload) {
                    is HomeGenrePayload.ShowData -> {
                        (holder as? HomeGenreViewHolder)?.showGenre(payload.genres)
                    }
                    is HomeGenrePayload.ShowError -> {
                        (holder as? HomeGenreViewHolder)?.showErrorGenre()
                    }
                }
            }
        )

        payloadByType<HomeMovieNowPlayingPayload>(
            payloads = payloads,
            emptyPayload = { onBindViewHolder(holder, position) },
            onPayload = { payload ->
                when (payload) {
                    is HomeMovieNowPlayingPayload.ShowData -> {
                        (holder as? HomeNowPlayingViewHolder)?.showMovie(payload.movies)
                    }
                    is HomeMovieNowPlayingPayload.ShowEmpty -> {
                        (holder as? HomeNowPlayingViewHolder)?.showEmptyMovie()
                    }
                    is HomeMovieNowPlayingPayload.ShowError -> {
                        (holder as? HomeNowPlayingViewHolder)?.showErrorMovie()
                    }
                }
            }
        )

        payloadByType<HomeMovieTopRatedPayload>(
            payloads = payloads,
            emptyPayload = { onBindViewHolder(holder, position) },
            onPayload = { payload ->
                when (payload) {
                    is HomeMovieTopRatedPayload.ShowData -> {
                        (holder as? HomeTopRatedViewHolder)?.showMovie(payload.movies)
                    }
                    is HomeMovieTopRatedPayload.ShowEmpty -> {
                        (holder as? HomeTopRatedViewHolder)?.showEmptyMovie()
                    }
                    is HomeMovieTopRatedPayload.ShowError -> {
                        (holder as? HomeTopRatedViewHolder)?.showErrorMovie()
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

    override fun releaseResource() {
        movieGenreListener = null
        movieNowPlayingListener = null
        movieTopRatedListener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    fun registerListener(
        movieGenreListener: MovieGenreListener?,
        movieNowPlayingListener: MovieNowPlayingListener?,
        movieTopRatedListener: MovieTopRatedListener?
    ) {
        this.movieGenreListener = movieGenreListener
        this.movieNowPlayingListener = movieNowPlayingListener
        this.movieTopRatedListener = movieTopRatedListener
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<HomeViewType>() {
            override fun areItemsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: HomeViewType, newItem: HomeViewType): Boolean =
                oldItem == newItem
        }
    }
}
