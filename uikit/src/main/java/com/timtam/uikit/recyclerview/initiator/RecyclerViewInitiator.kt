package com.timtam.uikit.recyclerview.initiator

import androidx.recyclerview.widget.RecyclerView
import com.timtam.uikit.extension.RecyclerViewAdapter

class RecyclerViewInitiator<T : RecyclerViewAdapter> private constructor(
    builder: Builder<T>
) {
    private val recyclerView: RecyclerView?
    private val onRegisterListener: (T.() -> Unit)?
    private val activeAdapter: (() -> T)?
    private val setupWithAdapter: ((RecyclerView.() -> Unit))?

    init {
        recyclerView = builder.recyclerView
        activeAdapter = builder.activeAdapter
        onRegisterListener = builder.onRegisterListener
        setupWithAdapter = builder.setupWithAdapter
    }

    @Suppress("UNCHECKED_CAST")
    fun initialize() = recyclerView?.run {
        if (adapter == null) {
            adapter = activeAdapter?.invoke()
            setupWithAdapter?.invoke(this)
        }
        onRegisterListener?.invoke(adapter as? T ?: return@run)
    }

    class Builder<T : RecyclerViewAdapter> {
        var recyclerView: RecyclerView? = null
            private set

        var activeAdapter: (() -> T)? = null
            private set

        var onRegisterListener: (T.() -> Unit)? = null
            private set
        var setupWithAdapter: ((RecyclerView.() -> Unit))? = null
            private set

        fun withRecyclerView(view: RecyclerView?) = apply { this.recyclerView = view }

        fun withListener(block: T.() -> Unit) = apply { this.onRegisterListener = block }

        fun withAdapter(adapter: () -> T) = apply { this.activeAdapter = adapter }

        fun onAttachedAdapter(block: RecyclerView.() -> Unit) = apply { this.setupWithAdapter = block }

        fun build() = RecyclerViewInitiator(this)
    }
}
