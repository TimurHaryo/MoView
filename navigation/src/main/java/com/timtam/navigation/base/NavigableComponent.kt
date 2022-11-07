package com.timtam.navigation.base

import com.timtam.navigation.model.NavigationFlow

interface NavigableComponent {
    fun navigateToFlow(flow: NavigationFlow)
}
