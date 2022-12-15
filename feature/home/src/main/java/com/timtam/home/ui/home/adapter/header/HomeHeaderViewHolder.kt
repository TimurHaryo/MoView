package com.timtam.home.ui.home.adapter.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.home.databinding.ItemHomeSectionHeaderBinding

class HomeHeaderViewHolder(
    private val binding: ItemHomeSectionHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup) = HomeHeaderViewHolder(
            ItemHomeSectionHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}
