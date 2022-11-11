package com.timtam.navigation.base

import androidx.navigation.NavController

interface NavigableComponent {
    fun navigateToFlow(flow: NavigationFlow, navController: NavController?)
}
