package com.timtam.navigation.extension

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.model.DeeplinkDestination
import com.timtam.navigation.model.NavigationFlow

fun buildDeeplink(destination: DeeplinkDestination) =
    NavDeepLinkRequest.Builder
        .fromUri(destination.address.toUri())
        .build()

fun NavController.deeplinkNavigateTo(
    deeplinkDestination: DeeplinkDestination,
    isPopUpTo: Boolean = false
) {
    val builder = NavOptions.Builder()

    if (isPopUpTo) {
        builder.setPopUpTo(graph.startDestination, true)
    }

    navigate(
        buildDeeplink(deeplinkDestination),
        builder.build()
    )
}

fun Fragment.startNavigation(flow: NavigationFlow): Boolean {
    if (activity == null && activity !is NavigableComponent) return false

    (activity as NavigableComponent).navigateToFlow(flow)
    return true
}
