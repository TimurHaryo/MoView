package com.timtam.initial.ui.onboarding

import android.os.Bundle
import android.view.View
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.initial.R
import com.timtam.initial.databinding.FragmentOnBoardingBinding
import com.timtam.navigation.extension.startNavigation

class OnBoardingFragment : LifecycleFragment(R.layout.fragment_on_boarding) {
    private val binding: FragmentOnBoardingBinding by viewLifecycleLazy(FragmentOnBoardingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (listOf(true, false).random()) {
            startNavigation(OnBoardingFragmentDirections.actionOnBoardingFragmentToMainFragment())
        }

        var count = 0
        binding.tvOnBoarding.setOnClickListener {
            binding.tvOnBoarding.text = "${binding.tvOnBoarding.text} $count"
            if (count == 2) {
                startNavigation(OnBoardingFragmentDirections.actionOnBoardingFragmentToMainFragment())
            }
            count++
        }
    }
}
