package com.timtam.common.delegation.inflater

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.okcredit.layout_inflator.OkLayoutInflater

class AsyncInflaterImpl : AsyncInflater {
    private var okLayoutInflater: OkLayoutInflater? = null

    init {
        when (val parentType = this as AsyncInflater) {
            is Activity -> okLayoutInflater = OkLayoutInflater(parentType as Activity)
            is Fragment -> okLayoutInflater = OkLayoutInflater(parentType as Fragment)
            is View -> okLayoutInflater = OkLayoutInflater(parentType as View)
            else -> Throwable("This interface can only be implemented by Activity, Fragment, and View")
        }
    }

    override fun inflate(layoutId: Int, container: ViewGroup?, inflated: (View) -> Unit) {
        okLayoutInflater?.inflate(layoutId, container, inflated)
    }

    override fun inflate(layoutId: Int, inflated: (View) -> Unit) {
        inflate(layoutId, null, inflated)
    }
}
