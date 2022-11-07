package com.timtam.navigation.base

import androidx.navigation.NavController
import com.timtam.navigation.model.NavigationFlow

class Navigator : NavigableComponent {
    private var navController: NavController? = null

    override fun navigateToFlow(flow: NavigationFlow) {
//        TODO("Not yet implemented")
    }

    fun initNavigator(controller: NavController) {
        navController = controller
    }
}
