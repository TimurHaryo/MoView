package com.timtam.common.abstraction

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.timtam.common_kotlin.extension.isNull

abstract class BindingActivity<T : ViewDataBinding> : AppCompatActivity() {
    private var _binding: T? = null

    protected val binding: T
        get() {
            if (_binding.isNull()) {
                throw IllegalArgumentException("${this.javaClass.simpleName} does not initialize data binding")
            }
            return _binding!!
        }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun setBinding(binding: T?) {
        _binding = binding
        _binding?.executePendingBindings()
    }
}
