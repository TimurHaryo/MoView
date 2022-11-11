package com.timtam.navigation.extension

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.base.NavigationFlow
import com.timtam.navigation.model.deeplink.DeeplinkDestination

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
    val controller = navController()
    if (controller == null || this !is NavigableComponent) return false

    (this as NavigableComponent).navigateToFlow(flow, controller)
    return true
}

fun Fragment.startNavigation(direction: NavDirections, options: NavOptions? = null): Boolean {
    val controller = navController() ?: return false

    controller.navigate(direction, options)
    return true
}

fun Fragment.navController(): NavController? =
    try { findNavController() } catch (_: Throwable) { null }
