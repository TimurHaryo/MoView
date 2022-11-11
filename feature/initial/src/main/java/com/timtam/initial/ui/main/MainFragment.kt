package com.timtam.initial.ui.main

import android.os.Bundle
import android.view.View
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.initial.R
import com.timtam.initial.databinding.FragmentMainBinding
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.extension.startNavigation
import com.timtam.navigation.model.MainNavigationFlow
import com.timtam.navigation.navigator.MainNavigator

class MainFragment : LifecycleFragment(R.layout.fragment_main), NavigableComponent by MainNavigator() {

    private val binding: FragmentMainBinding by viewLifecycleLazy(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBlank.setOnClickListener {
            startNavigation(MainNavigationFlow.DetailMovie("HELLO WORLD!"))
        }
    }
}
