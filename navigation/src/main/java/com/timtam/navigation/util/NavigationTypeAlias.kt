package com.timtam.navigation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timtam.navigation.fragment.NavigableFragmentDelegate
import com.timtam.navigation.navigator.NavigationEvent

typealias OnBackEvent = ((superBack: NavigableFragmentDelegate.SuperBackListener) -> Boolean)

internal typealias MutableNavigationRouter = MutableLiveData<RouterEvent<NavigationEvent>>

internal typealias NavigationRouter = LiveData<RouterEvent<NavigationEvent>>
