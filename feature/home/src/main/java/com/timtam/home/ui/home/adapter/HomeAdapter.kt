package com.timtam.home.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.R
import com.timtam.home.ui.genre.HomeGenreViewHolder
import com.timtam.home.ui.genre.adapter.GenreListener
import com.timtam.home.ui.genre.payload.HomeGenrePayload
import com.timtam.home.ui.header.HomeHeaderViewHolder
import com.timtam.home.ui.nowplaying.HomeNowPlayingViewHolder
import com.timtam.home.ui.home.adapter.payload.HomePayload
import com.timtam.home.ui.toprated.HomeTopRatedViewHolder
import com.timtam.home.type.HomeViewType
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

    private var genreListener: GenreListener? = null

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
            is HomeGenreViewHolder -> holder.run {
                setListener(genreListener)
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
        genreListener = null
        executeToValidHolders(host) { holder ->
            if (holder is DetachableResource) holder.releaseResource()
        }
    }

    fun registerListener(genreListener: GenreListener?) {
        this.genreListener = genreListener
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
