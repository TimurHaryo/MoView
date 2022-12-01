package com.timtam.initial.ui.onboarding

import android.os.Bundle
import android.view.View
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.initial.R
import com.timtam.initial.databinding.FragmentOnBoardingBinding
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.extension.startNavigation
import com.timtam.navigation.navigator.MainNavigator

class OnBoardingFragment : LifecycleFragment(R.layout.fragment_on_boarding), NavigableComponent by MainNavigator() {
    private val binding: FragmentOnBoardingBinding by viewLifecycleLazy(FragmentOnBoardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var count = 0
        binding.tvOnBoarding.setOnClickListener {
            binding.tvOnBoarding.text = buildString {
                append(binding.tvOnBoarding.text)
                append(" ")
                append(count)
            }
            if (count == 2) {
                startNavigation(OnBoardingFragmentDirections.actionOnBoardingFragmentToMainFragment())
            }
            count++
        }
    }
}
