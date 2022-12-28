package com.timtam.home.ui.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.databinding.ItemHomeSectionTopRatedBinding

class HomeTopRatedViewHolder(
    private val binding: ItemHomeSectionTopRatedBinding
) : RecyclerView.ViewHolder(binding.root) {

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
