package com.timtam.navigation.navigator

import android.util.Log
import androidx.navigation.NavController
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.base.NavigationFlow
import com.timtam.navigation.model.MainNavigationFlow

class MainNavigator : NavigableComponent {
    override fun navigateToFlow(flow: NavigationFlow, navController: NavController?) {
        when (flow) {
            is MainNavigationFlow.DetailMovie -> Log.i("MainNavigator", "${flow.id} -> $navController")
        }
    }
}
