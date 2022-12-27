package com.timtam.uikit.recyclerview.cachecontrol

import androidx.recyclerview.widget.RecyclerView

class RecyclerHolderCaching : RecyclerHolderCacheable {

    private val validHolderPositions = mutableSetOf<Int>()

    override fun addValidHolderPosition(position: Int) {
        if (position < 0) return
        validHolderPositions.add(position)
    }

    override fun removeValidHolderPosition(position: Int) {
        validHolderPositions.remove(position)
    }

    @Suppress("UNCHECKED_CAST")
    override fun executeToValidHolders(recyclerView: RecyclerView?, block: (RecyclerView.ViewHolder) -> Unit) =
        recyclerView?.run {
            validHolderPositions.forEach { position ->
                getChildAt(position)?.let { view ->
                    block(getChildViewHolder(view) ?: return@let)
                }
            }
        } ?: Unit

}
