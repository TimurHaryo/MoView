package com.timtam.common.abstraction

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.timtam.common.lifecycle.UiDataLifecycleAware

abstract class LifecycleFragment : Fragment() {
    private val uiDataLifecycle: UiDataLifecycleAware by lazy {
        UiDataLifecycleAware(viewLifecycleOwner.lifecycle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiDataLifecycle.apply {
            setOnInitView(::onHandleUi)
            setOnFetchData(::onHandleData)
            registerLifecycleAware()
        }
    }

    override fun onDestroyView() {
        uiDataLifecycle.unregisterLifecycleAware()
        super.onDestroyView()
    }

    protected open fun onHandleUi() = Unit
    protected open fun onHandleData() = Unit
}
