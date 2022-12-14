package com.timtam.uikit.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.timtam.uikit.extension.setVisibleElseGone

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:isVisibleOrGone")
    fun setIsVisibleOrGone(view: View, clause: Boolean?) {
        clause?.let(view::setVisibleElseGone)
    }
}
