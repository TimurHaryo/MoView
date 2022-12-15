package com.timtam.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.common_android.extension.i
import com.timtam.common_android.extension.viewLifecycleLazy
import com.timtam.home.databinding.FragmentHomeBinding
import com.timtam.home.ui.home.adapter.HomeAdapter
import com.timtam.home.ui.model.HomeViewType

class HomeFragment :
    LifecycleFragment<FragmentHomeBinding>(),
    FragmentRetainable by FragmentRetainer() {

    private val homeAdapter by viewLifecycleLazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return oneTimeRenderer {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            _binding?.lifecycleOwner = viewLifecycleOwner
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTimeInitView {
            i { "HELLO HOME! ${HomeViewType.defaultOrder}" }
            setupRecyclerView()
        }
    }

    private fun setupRecyclerView() = with(binding.rvHomeContent) {
        adapter = homeAdapter.apply {
            submitList(HomeViewType.defaultOrder)
        }
    }
}
