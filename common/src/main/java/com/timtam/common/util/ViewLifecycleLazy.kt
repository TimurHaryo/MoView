package com.timtam.common.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.timtam.common_kotlin.extension.isNotNull
import java.lang.ref.WeakReference

internal class ViewLifecycleLazy<out T : Any>(
    private val fragment: WeakReference<Fragment>,
    private val initializer: () -> T
) : Lazy<T>, LifecycleEventObserver {
    private var cached: T? = null

    override val value: T
        get() {
            return cached ?: run {
                val newValue = initializer()
                cached = newValue
                fragment.get()?.viewLifecycleOwner?.lifecycle?.addObserver(this)
                newValue
            }
        }

    override fun isInitialized(): Boolean = cached.isNotNull()

    override fun toString(): String = cached.toString()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            cached = null
            fragment.apply {
                get()?.viewLifecycleOwner?.lifecycle?.removeObserver(this@ViewLifecycleLazy)
                clear()
            }
        }
    }
}
