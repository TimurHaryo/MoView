package com.timtam.navigation.direction

import com.timtam.navigation.base.NavigationFlow

sealed class MainNavigationFlow : NavigationFlow {
    object Home : MainNavigationFlow()
    object Review : MainNavigationFlow()
    object Collection : MainNavigationFlow()
}
