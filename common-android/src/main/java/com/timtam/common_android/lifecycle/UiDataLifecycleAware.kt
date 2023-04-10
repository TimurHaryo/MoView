package com.timtam.common_android.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

internal class UiDataLifecycleAware(private val lifecycle: Lifecycle) : LifecycleEventObserver {

    private var hasBeenFetched: Boolean = false

    private var onFetchData: (() -> Unit)? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> fetchData()
            else -> Unit
        }
    }

    fun registerLifecycleAware() {
        this.lifecycle.addObserver(this)
    }

    fun unregisterLifecycleAware() {
        this.lifecycle.removeObserver(this)
    }

    fun setOnFetchData(action: () -> Unit) {
        this.onFetchData = action
    }

    private fun fetchData() {
        if (!hasBeenFetched) {
            hasBeenFetched = true
            onFetchData?.invoke()
        }
    }
}
