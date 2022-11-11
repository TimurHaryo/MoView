package com.timtam.initial.ui.main

import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.initial.databinding.FragmentMainBinding
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.extension.startNavigation
import com.timtam.navigation.model.MainNavigationFlow
import com.timtam.navigation.navigator.MainNavigator

class MainFragment : LifecycleFragment(), NavigableComponent by MainNavigator() {

    private val binding: FragmentMainBinding by viewLifecycleLazy(FragmentMainBinding::bind)

    override fun onHandleUi() {
        super.onHandleUi()
        binding.tvBlank.setOnClickListener {
            startNavigation(MainNavigationFlow.DetailMovie("HELLO WORLD!"))
        }
    }
}
