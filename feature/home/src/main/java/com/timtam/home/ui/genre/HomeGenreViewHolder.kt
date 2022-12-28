package com.timtam.home.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.home.databinding.ItemHomeSectionGenreBinding
import com.timtam.home.ui.genre.adapter.GenreAdapter
import com.timtam.home.ui.genre.adapter.GenreListener
import com.timtam.uikit.extension.detachFromAdapter
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.recyclerview.decoration.AdaptiveSpacingItemDecoration
import com.timtam.uikit.recyclerview.resourceful.AttachableResource
import com.timtam.uikit.recyclerview.resourceful.DetachableResource
import com.timtam.uikit.R as uikitR

class HomeGenreViewHolder(
    private val binding: ItemHomeSectionGenreBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<GenreListener>,
    DetachableResource {

    private var listener: GenreListener? = null

    private val genres: ArrayList<GenreHomeItem> = arrayListOf()

    private var genreAdapter: GenreAdapter? = null

    override fun setListener(resource: GenreListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        genreAdapter?.releaseResource()
        genreAdapter = null
        listener = null
        binding.rvHomeGenre.detachFromAdapter()
    }

    fun bind() = with(binding) {
        rvHomeGenre.preAttach {
            genreAdapter = GenreAdapter().apply {
                setListener(listener)
            }
            adapter = genreAdapter
            layoutManager = GridLayoutManager(binding.root.context, 3)
            addItemDecoration(
                AdaptiveSpacingItemDecoration.create(
                    binding.root.context.resources.getDimensionPixelSize(uikitR.dimen.space_32),
                    binding.root.context.resources.getDimensionPixelSize(uikitR.dimen.space_12),
                    false
                )
            )
        }

        tvHomeGenreSeeAll.setOnClickListener { listener?.onMoreClick() }
    }

    fun showGenre(data: List<GenreHomeItem>) {
        genres.clear()
        genres.addAll(data)

        genreAdapter?.submitList(genres)
    }

    fun showErrorGenre() {
        e { "GENRE ITEM ERROR" }
    }

    companion object {
        fun create(parent: ViewGroup) = HomeGenreViewHolder(
            ItemHomeSectionGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
