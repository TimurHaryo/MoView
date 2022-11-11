package com.timtam.navigation.model

import com.timtam.navigation.base.NavigationFlow

sealed class MainNavigationFlow : NavigationFlow {
    data class DetailMovie(val id: String) : MainNavigationFlow()
}
