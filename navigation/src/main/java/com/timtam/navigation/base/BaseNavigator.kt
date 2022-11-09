package com.timtam.navigation.base

import androidx.navigation.NavController

abstract class BaseNavigator : NavigableComponent {
    protected var navController: NavController? = null

    fun initNavigator(controller: NavController) {
        navController = controller
    }
}
