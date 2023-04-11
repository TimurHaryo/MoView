package com.timtam.initial.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.initial.databinding.FragmentOnBoardingBinding
import com.timtam.navigation.fragment.NavigableFragmentDelegate
import com.timtam.navigation.fragment.NavigableFragmentDelegateImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment :
    LifecycleFragment<FragmentOnBoardingBinding>(),
    FragmentRetainable by FragmentRetainer(),
    NavigableFragmentDelegate by NavigableFragmentDelegateImpl() {

    private val viewModel by viewModels<OnBoardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerNavigableComponent(viewModel, this)
    }

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
                    viewModel.openMainMenu()
                }
                count++
            }
        }
    }
}
