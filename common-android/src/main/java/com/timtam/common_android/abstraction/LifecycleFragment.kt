package com.timtam.common_android.abstraction

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.timtam.common_android.lifecycle.UiDataLifecycleAware
import com.timtam.common_kotlin.extension.isNull

abstract class LifecycleFragment<T : ViewDataBinding> : Fragment() {
    protected var _binding: T? = null

    protected val binding: T
        get() {
            if (_binding.isNull()) {
                throw IllegalArgumentException("${this.javaClass.simpleName} does not initialize data binding")
            }
            return _binding!!
        }

    private val uiDataLifecycle: UiDataLifecycleAware by lazy {
        UiDataLifecycleAware(viewLifecycleOwner.lifecycle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiDataLifecycle.apply {
            setOnFetchData(::onHandleData)
            registerLifecycleAware()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiDataLifecycle.unregisterLifecycleAware()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    protected open fun onHandleData() = Unit
}
