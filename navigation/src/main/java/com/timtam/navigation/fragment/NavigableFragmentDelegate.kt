package com.timtam.navigation.fragment

import androidx.fragment.app.Fragment
import com.timtam.navigation.util.OnBackEvent
import com.timtam.navigation.viewmodel.NavigableViewModelDelegate

interface NavigableFragmentDelegate {

    /**
     * call this method before ON_CREATE lifecycle ex: onCreate()
     *
     * @param viewModel view model that is a NavigableViewModelDelegate to use router
     * @param fragment bind fragment to navigable lifecycle
     */
    fun registerNavigableComponent(
        viewModel: NavigableViewModelDelegate,
        fragment: Fragment
    )

    /**
     * call this method before ON_CREATE lifecycle ex: onCreate()
     *
     * @param onBackEvent return true if it should navigate back after invoke or false if not
     */
    fun registerOnBackEvent(onBackEvent: OnBackEvent)

    fun interface SuperBackListener {
        fun superBack()
    }
}
