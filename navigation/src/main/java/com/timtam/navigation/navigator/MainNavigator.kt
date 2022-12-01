package com.timtam.navigation.navigator

import androidx.navigation.NavController
import com.timtam.navigation.MainNavGraphDirections
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.base.NavigationFlow
import com.timtam.navigation.direction.MainNavigationFlow

class MainNavigator : NavigableComponent {
    override fun navigateToFlow(flow: NavigationFlow, navController: NavController?) {
        when (flow) {
            MainNavigationFlow.Home -> navController?.navigate(MainNavGraphDirections.actionToHomeFlow())
            MainNavigationFlow.Review -> navController?.navigate(MainNavGraphDirections.actionToReviewFlow())
            MainNavigationFlow.Collection -> navController?.navigate(MainNavGraphDirections.actionToCollectionFlow())
            else -> Unit
        }
    }
}
