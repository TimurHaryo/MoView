package com.timtam.navigation.viewmodel

import com.timtam.navigation.navigator.NavigationEvent
import com.timtam.navigation.util.NavigationRouter
import kotlinx.coroutines.CoroutineScope

interface NavigableViewModelDelegate {

    val router: NavigationRouter

    fun registerScope(scope: CoroutineScope)

    fun navigateTo(event: NavigationEvent)
}
