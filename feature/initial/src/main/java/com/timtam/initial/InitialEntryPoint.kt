package com.timtam.initial

import com.timtam.initial.ui.onboarding.OnBoardingFragmentDirections
import com.timtam.initial_contract.navigation.InitialFeatureDirection
import com.timtam.navigation.navigator.NavigationEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InitialEntryPoint @Inject constructor() : InitialFeatureDirection {
    override fun openMainMenu(): NavigationEvent =
        NavigationEvent.To(OnBoardingFragmentDirections.actionOnBoardingFragmentToMainFragment())
}
