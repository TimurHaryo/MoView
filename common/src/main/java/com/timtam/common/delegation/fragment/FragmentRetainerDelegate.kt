package com.timtam.common.delegation.fragment

import android.view.View

interface FragmentRetainerDelegate {

    fun oneTimeRenderer(inflater: () -> View): View

    fun initView(block: () -> Unit)
}
