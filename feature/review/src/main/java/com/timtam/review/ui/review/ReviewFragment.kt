package com.timtam.review.ui.review

import android.os.Bundle
import android.view.View
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.extension.i
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.review.R
import com.timtam.review.databinding.FragmentReviewBinding

class ReviewFragment : LifecycleFragment(R.layout.fragment_review) {

    private val binding: FragmentReviewBinding by viewLifecycleLazy(FragmentReviewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        i { "HELLO REVIEW!" }
    }
}
