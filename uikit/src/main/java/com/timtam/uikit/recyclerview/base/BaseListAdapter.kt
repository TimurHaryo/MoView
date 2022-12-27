package com.timtam.uikit.recyclerview.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timtam.uikit.extension.weaken
import java.lang.ref.WeakReference

abstract class BaseListAdapter<Item, Holder : RecyclerView.ViewHolder>(
    differ: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, Holder>(differ) {

    private var _recyclerView: WeakReference<RecyclerView>? = null
    protected val host: RecyclerView? get() = _recyclerView?.get()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setHost(recyclerView.weaken())
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        releaseHost()
    }

    private fun setHost(weakView: WeakReference<RecyclerView>) {
        _recyclerView = weakView
    }

    private fun releaseHost() {
        _recyclerView?.clear()
        _recyclerView = null
    }
}
