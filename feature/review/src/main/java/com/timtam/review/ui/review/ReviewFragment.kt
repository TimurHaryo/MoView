package com.timtam.review.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.delegation.fragment.FragmentRetainerDelegate
import com.timtam.common.delegation.fragment.FragmentRetainerDelegateImpl
import com.timtam.common.extension.i
import com.timtam.review.databinding.FragmentReviewBinding

class ReviewFragment :
    LifecycleFragment<FragmentReviewBinding>(),
    FragmentRetainerDelegate by FragmentRetainerDelegateImpl() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return oneTimeRenderer {
            _binding = FragmentReviewBinding.inflate(inflater, container, false)
            _binding?.lifecycleOwner = viewLifecycleOwner
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTimeInitView {
            i { "HELLO REVIEW!" }
        }
    }
}
