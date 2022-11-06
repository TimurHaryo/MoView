package com.timtam.common.delegation.inflater

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

interface AsyncInflater {
    fun inflate(@LayoutRes layoutId: Int, container: ViewGroup?, inflated: (View) -> Unit)
    fun inflate(@LayoutRes layoutId: Int, inflated: (View) -> Unit)
}
