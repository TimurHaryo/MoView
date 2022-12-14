package com.timtam.uikit.recyclerview.base

import androidx.recyclerview.widget.RecyclerView
import com.timtam.uikit.extension.RecyclerViewAdapter
import java.lang.ref.WeakReference

class RecyclerViewInitiator<T : RecyclerViewAdapter> private constructor(
    builder: Builder<T>
) {
    private val recyclerView: WeakReference<RecyclerView>?
    private val onRegisterListener: (T.() -> Unit)?
    private val activeAdapter: (() -> T)?
    private val setupWithAdapter: ((RecyclerView.() -> Unit))?

    private var cacheAdapter: T? = null

    init {
        recyclerView = builder.recyclerView
        activeAdapter = builder.activeAdapter
        onRegisterListener = builder.onRegisterListener
        setupWithAdapter = builder.setupWithAdapter
    }

    fun initialize() {
        recyclerView?.get()?.apply {
            if (adapter == null) {
                cacheAdapter = activeAdapter?.invoke()
                adapter = cacheAdapter
                setupWithAdapter?.invoke(this)
            }
        }
        onRegisterListener?.invoke(cacheAdapter ?: return)
    }

    fun clear() {
        recyclerView?.clear()
    }

    class Builder<T : RecyclerViewAdapter> {
        var recyclerView: WeakReference<RecyclerView>? = null
            private set

        var activeAdapter: (() -> T)? = null
            private set

        var onRegisterListener: (T.() -> Unit)? = null
            private set
        var setupWithAdapter: ((RecyclerView.() -> Unit))? = null
            private set

        fun withRecyclerView(view: WeakReference<RecyclerView>?) = apply { this.recyclerView = view }

        fun withListener(block: T.() -> Unit) = apply { this.onRegisterListener = block }

        fun withAdapter(adapter: () -> T) = apply { this.activeAdapter = adapter }

        fun onAttachedAdapter(block: RecyclerView.() -> Unit) = apply { this.setupWithAdapter = block }

        fun build() = RecyclerViewInitiator(this)
    }
}
