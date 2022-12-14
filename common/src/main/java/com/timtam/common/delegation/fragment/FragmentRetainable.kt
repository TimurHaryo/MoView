package com.timtam.common.delegation.fragment

import android.view.View

interface FragmentRetainable {

    fun oneTimeRenderer(inflater: () -> View): View

    fun oneTimeInitView(block: () -> Unit)
}
