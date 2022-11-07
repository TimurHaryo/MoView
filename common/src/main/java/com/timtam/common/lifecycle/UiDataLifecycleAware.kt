package com.timtam.common.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

internal class UiDataLifecycleAware(private val lifecycle: Lifecycle) : LifecycleEventObserver {

    private var hasBeenFetched: Boolean = false

    private var onInitView: (() -> Unit)? = null
    private var onFetchData: (() -> Unit)? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> initView()
            Lifecycle.Event.ON_RESUME -> fetchData()
            else -> Unit
        }
    }

    fun registerLifecycleAware() {
        this.lifecycle.addObserver(this)
    }

    fun unregisterLifecycleAware() {
        this.lifecycle.removeObserver(this)
    }

    fun setOnInitView(action: () -> Unit) {
        this.onInitView = action
    }

    fun setOnFetchData(action: () -> Unit) {
        this.onFetchData = action
    }

    private fun initView() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            onInitView?.invoke()
        }
    }

    private fun fetchData() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED) && !hasBeenFetched) {
            onFetchData?.invoke()
            hasBeenFetched = true
        }
    }
}
