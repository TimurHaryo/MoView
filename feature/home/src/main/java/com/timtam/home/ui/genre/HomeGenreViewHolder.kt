package com.timtam.home.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.e
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.home.databinding.ItemHomeSectionGenreBinding
import com.timtam.home.ui.genre.adapter.GenreAdapter
import com.timtam.home.ui.genre.adapter.MovieGenreListener
import com.timtam.uikit.extension.preAttach
import com.timtam.uikit.recyclerview.decoration.AdaptiveSpacingItemDecoration
import com.timtam.uikit.recyclerview.resource.AttachableResource
import com.timtam.uikit.recyclerview.resource.DetachableResource
import com.timtam.uikit.R as uikitR

class HomeGenreViewHolder(
    private val binding: ItemHomeSectionGenreBinding
) :
    RecyclerView.ViewHolder(binding.root),
    AttachableResource<MovieGenreListener>,
    DetachableResource {

    private var listener: MovieGenreListener? = null

    private var genreAdapter: GenreAdapter? = null

    override fun setListener(resource: MovieGenreListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        genreAdapter?.releaseResource()
        genreAdapter = null
        listener = null
    }

    fun bind(data: List<GenreHomeItem>) = with(binding) {
        setupRecyclerView()
        (rvHomeGenre.adapter as? GenreAdapter)?.submitList(data)

        tvHomeGenreSeeAll.setOnClickListener { listener?.onMoreClick() }
    }

    fun showGenre(data: List<GenreHomeItem>) = with(binding.rvHomeGenre) {
        setupRecyclerView()
        (adapter as? GenreAdapter)?.submitList(data)
    }

    fun showErrorGenre() {
        e { "GENRE ITEM ERROR" }
    }

    private fun setupRecyclerView() = with(binding.rvHomeGenre) {
        preAttach {
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
