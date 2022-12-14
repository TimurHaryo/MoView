package com.timtam.common.delegation.fragment

import android.view.View
import com.timtam.common_kotlin.extension.isNull

class FragmentRetainer : FragmentRetainable {

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

    override fun oneTimeInitView(block: () -> Unit) {
        if (isFirstTimeInitializeView) block()
    }
}
