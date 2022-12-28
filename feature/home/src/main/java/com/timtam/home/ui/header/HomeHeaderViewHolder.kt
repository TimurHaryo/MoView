package com.timtam.home.ui.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timtam.common_android.extension.greeting
import com.timtam.home.databinding.ItemHomeSectionHeaderBinding
import com.timtam.home.ui.header.listener.HomeHeaderListener
import com.timtam.uikit.extension.context
import com.timtam.uikit.recyclerview.resourceful.AttachableResource
import com.timtam.uikit.recyclerview.resourceful.DetachableResource

class HomeHeaderViewHolder(
    private val binding: ItemHomeSectionHeaderBinding
) : RecyclerView.ViewHolder(binding.root),
    AttachableResource<HomeHeaderListener>,
    DetachableResource {

    private var listener: HomeHeaderListener? = null

    override fun setListener(resource: HomeHeaderListener?) {
        this.listener = resource
    }

    override fun releaseResource() {
        listener = null
    }

    fun bind() = with(binding) {
        layoutHomeArrangement.mcvHomeArrangement.setOnClickListener { listener?.onArrangeClick() }
        layoutHomeSearchBar.mcvHomeStaticSearchBar.setOnClickListener { listener?.onSearchBoxClick() }

        tvHomeGreeting.text = context.greeting()
        tvHomeMantra.text = "[TEST] Progress, Not perfection."
    }

    fun showMantra(mantra: String) {
        binding.tvHomeMantra.text = mantra
    }

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
