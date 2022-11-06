package com.timtam.common.abstraction

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.timtam.common.extension.isNull

abstract class BindingFragment<T : ViewDataBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding: T
        get() {
            if (_binding.isNull()) {
                throw IllegalArgumentException("${this.javaClass.simpleName} does not initialize data binding")
            }
            return _binding!!
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun setBinding(binding: T) {
        _binding = binding
        _binding?.executePendingBindings()
    }
}
