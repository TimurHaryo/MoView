package com.timtam.common.delegation.inflater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.okcredit.layout_inflator.OkLayoutInflater

class AsyncInflaterImpl : AsyncInflater {
    private var okLayoutInflater: OkLayoutInflater? = null

    constructor(context: Context) {
        okLayoutInflater = OkLayoutInflater(context)
    }

    constructor(fragment: Fragment) {
        okLayoutInflater = OkLayoutInflater(fragment)
    }

    constructor(view: View) {
        okLayoutInflater = OkLayoutInflater(view)
    }

    override fun inflate(layoutId: Int, container: ViewGroup?, inflated: (View) -> Unit) {
        okLayoutInflater?.inflate(layoutId, container, inflated)
    }

    override fun inflate(layoutId: Int, inflated: (View) -> Unit) {
        inflate(layoutId, null, inflated)
    }
}
