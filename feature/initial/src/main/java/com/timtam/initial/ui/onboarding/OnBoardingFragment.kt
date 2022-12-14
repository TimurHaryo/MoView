package com.timtam.initial.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.delegation.fragment.FragmentRetainable
import com.timtam.common.delegation.fragment.FragmentRetainer
import com.timtam.initial.databinding.FragmentOnBoardingBinding
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.extension.startNavigation
import com.timtam.navigation.navigator.MainNavigator

class OnBoardingFragment :
    LifecycleFragment<FragmentOnBoardingBinding>(),
    FragmentRetainable by FragmentRetainer(),
    NavigableComponent by MainNavigator() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = oneTimeRenderer {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTimeInitView {
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
}
