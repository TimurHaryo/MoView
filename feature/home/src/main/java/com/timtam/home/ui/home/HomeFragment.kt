package com.timtam.home.ui.home

import android.os.Bundle
import android.view.View
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.extension.i
import com.timtam.common.util.viewLifecycleLazy
import com.timtam.home.R
import com.timtam.home.databinding.FragmentHomeBinding

class HomeFragment : LifecycleFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewLifecycleLazy(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        i { "HELLO HOME!" }
    }
}
