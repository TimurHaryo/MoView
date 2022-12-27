package com.timtam.uikit.extension

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.databinding.ViewStubProxy
import java.lang.ref.WeakReference

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.setVisibleElseGone(clause: Boolean) {
    if (clause) visible() else gone()
}

fun <V> V.weaken() where V : View = WeakReference(this)

inline fun <reified T : ViewDataBinding> bindingStubType(
    proxy: ViewStubProxy?,
    crossinline onBind: T.() -> Unit
) {
    proxy?.binding?.let { if (it is T) it.onBind() }
}

inline fun ViewStubProxy.inflateIf(
    clause: Boolean,
    crossinline onInflated: () -> Unit
) {
    setOnInflateListener { _, _ ->
        onInflated()
        binding?.root?.setVisibleElseGone(clause)
    }

    if (isInflated) {
        binding?.root?.setVisibleElseGone(clause)
        return
    }

    if (clause && !isInflated) viewStub?.inflate()
}
