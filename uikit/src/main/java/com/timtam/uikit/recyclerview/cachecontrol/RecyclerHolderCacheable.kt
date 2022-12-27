package com.timtam.uikit.recyclerview.cachecontrol

import androidx.recyclerview.widget.RecyclerView

interface RecyclerHolderCacheable {

    fun addValidHolderPosition(position: Int)

    fun removeValidHolderPosition(position: Int)

    fun executeToValidHolders(
        recyclerView: RecyclerView?,
        block: (RecyclerView.ViewHolder) -> Unit
    )
}
