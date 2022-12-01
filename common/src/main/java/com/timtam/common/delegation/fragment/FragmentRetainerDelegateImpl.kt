package com.timtam.common.delegation.fragment

import android.view.View
import com.timtam.common.extension.isNull

class FragmentRetainerDelegateImpl : FragmentRetainerDelegate {

    private var isFirstTimeInitializeView = true
    private var rootView: View? = null

    override fun oneTimeRenderer(inflater: () -> View): View {
        if (rootView.isNull()) {
            isFirstTimeInitializeView = true
            rootView = inflater()
        } else {
            isFirstTimeInitializeView = false
        }
        return rootView ?: inflater()
    }

    override fun initView(block: () -> Unit) {
        if (isFirstTimeInitializeView) block()
    }
}
