package com.timtam.navigation.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.timtam.navigation.util.OnBackEvent
import com.timtam.navigation.viewmodel.NavigableViewModelDelegate

class NavigableFragmentDelegateImpl : NavigableFragmentDelegate, DefaultLifecycleObserver {

    private lateinit var navigableViewModel: NavigableViewModelDelegate
    private lateinit var navigableFragment: Fragment
    private var onBackListener: OnBackEvent? = null

    override fun registerNavigableComponent(
        viewModel: NavigableViewModelDelegate,
        fragment: Fragment
    ) {
        navigableViewModel = viewModel
        navigableFragment = fragment
        navigableFragment.lifecycle.addObserver(this@NavigableFragmentDelegateImpl)
    }

    override fun registerOnBackEvent(onBackEvent: OnBackEvent) {
        onBackListener = onBackEvent
    }

    override fun onStart(owner: LifecycleOwner) {
        with(navigableFragment) {
            activity?.onBackPressedDispatcher?.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (onBackListener != null) {
                            onBack()
                        } else {
                            isEnabled = false
                            activity?.onBackPressed()
                        }
                    }
                }
            )
        }
        super.onStart(owner)
    }

    override fun onCreate(owner: LifecycleOwner) {
        navigableViewModel.router.observe(owner) {
            it.getContentIfNotHandled()?.let { navigation ->
                navigation.navigate(navigableFragment) {
                    onBack()
                }
            }
        }
        super.onCreate(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        navigableFragment.lifecycle.removeObserver(this@NavigableFragmentDelegateImpl)
        super.onDestroy(owner)
    }

    private fun onBack() {
        val back = onBackListener?.invoke { superBack() } ?: true
        if (back) superBack()
    }

    private fun superBack() =
        with(navigableFragment) {
            if (!findNavController().navigateUp()) {
                requireActivity().finish()
            }
        }
}
