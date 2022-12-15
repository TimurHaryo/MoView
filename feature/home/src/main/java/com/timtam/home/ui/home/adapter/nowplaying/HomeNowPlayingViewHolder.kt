package com.timtam.home.ui.home.adapter.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.databinding.ItemHomeSectionPlayingBinding

class HomeNowPlayingViewHolder(
    private val binding: ItemHomeSectionPlayingBinding
) : RecyclerView.ViewHolder(binding.root) {

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
