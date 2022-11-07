package com.timtam.common.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.timtam.common.extension.isNotNull

fun <T : Any> Fragment.viewLifecycleLazy(initializer: (View) -> T): Lazy<T> =
    ViewLifecycleLazy(this, initializer)

private class ViewLifecycleLazy<out T : Any>(
    private val fragment: Fragment,
    private val initializer: (View) -> T
) : Lazy<T>, LifecycleEventObserver {
    private var cached: T? = null

    override val value: T
        get() {
            return cached ?: run {
                val newValue = initializer(fragment.requireView())
                cached = newValue
                fragment.viewLifecycleOwner.lifecycle.addObserver(this)
                newValue
            }
        }

    override fun isInitialized(): Boolean = cached.isNotNull()

    override fun toString(): String = cached.toString()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            cached = null
        }
    }
}
