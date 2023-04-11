package com.timtam.initial.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.initial_contract.navigation.InitialFeatureDirection
import com.timtam.navigation.viewmodel.NavigableViewModelDelegate
import com.timtam.navigation.viewmodel.NavigableViewModelDelegateImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val initialFeatureDirection: InitialFeatureDirection
) :
    ViewModel(),
    NavigableViewModelDelegate by NavigableViewModelDelegateImpl() {

    init {
        registerScope(viewModelScope)
    }

    fun openMainMenu() {
        navigateTo(initialFeatureDirection.openMainMenu())
    }
}
