package com.timtam.common.abstraction

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.timtam.common.lifecycle.UiDataLifecycleAware

abstract class LifecycleFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    private val uiDataLifecycle: UiDataLifecycleAware by lazy {
        UiDataLifecycleAware(viewLifecycleOwner.lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        uiDataLifecycle.apply {
            setOnFetchData(::onHandleData)
            registerLifecycleAware()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDataLifecycle.unregisterLifecycleAware()
    }

    protected open fun onHandleData() = Unit
}
