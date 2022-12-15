package com.timtam.home.ui.home.adapter.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.databinding.ItemHomeSectionGenreBinding

class HomeGenreViewHolder(
    private val binding: ItemHomeSectionGenreBinding
) : RecyclerView.ViewHolder(binding.root) {

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
