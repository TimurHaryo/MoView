package com.timtam.navigation.extension

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.timtam.navigation.deeplink.DeeplinkDestination

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
        builder.setPopUpTo(graph.startDestinationId, true)
    }

    navigate(
        buildDeeplink(deeplinkDestination),
        builder.build()
    )
}

fun Fragment.navController(): NavController? =
    try { findNavController() } catch (_: Throwable) { null }

internal fun NavController.safeNavigate(
    direction: NavDirections,
    extras: Navigator.Extras = FragmentNavigatorExtras()
) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction, extras) }
}
