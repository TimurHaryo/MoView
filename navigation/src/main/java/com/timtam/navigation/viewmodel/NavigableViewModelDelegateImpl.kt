package com.timtam.navigation.viewmodel

import com.timtam.navigation.navigator.NavigationEvent
import com.timtam.navigation.util.MutableNavigationRouter
import com.timtam.navigation.util.NavigationRouter
import com.timtam.navigation.util.RouterEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NavigableViewModelDelegateImpl : NavigableViewModelDelegate {

    private val _router = MutableNavigationRouter()
    override val router: NavigationRouter get() = _router

    private var viewModelScope: CoroutineScope? = null
    private var navigationJob: Job? = null

    override fun registerScope(scope: CoroutineScope) {
        viewModelScope = scope
    }

    override fun navigateTo(event: NavigationEvent) {
        if (viewModelScope == null) throw IllegalAccessException("call registerScope() first.")
        if (navigationJob != null) return

        navigationJob = viewModelScope!!.launch {
            _router.postValue(RouterEvent(event))
        }
        navigationJob = null
    }
}
