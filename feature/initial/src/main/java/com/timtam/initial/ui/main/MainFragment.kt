package com.timtam.initial.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.common_kotlin.extension.isNotNull
import com.timtam.feature_helper.extension.observeLiveData
import com.timtam.initial.R
import com.timtam.initial.databinding.FragmentMainBinding
import com.timtam.initial.model.type.MainTabType
import com.timtam.navigation.base.NavigableComponent
import com.timtam.navigation.navigator.MainNavigator
import com.timtam.navigation.util.SafeNavHostFragment

class MainFragment :
    LifecycleFragment<FragmentMainBinding>(),
    FragmentRetainable by FragmentRetainer(),
    NavigableComponent by MainNavigator() {

    private val viewModel: MainViewModel by viewModels()

    private var prevTab: MainTabType? = null

    private val MainTabType.fragment get() = childFragmentManager.findFragmentByTag(name)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return oneTimeRenderer {
            _binding = FragmentMainBinding.inflate(inflater, container, false)
            _binding?.lifecycleOwner = viewLifecycleOwner
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTimeInitView {
            setupGraph()
            setupObserver()
        }
    }

    private fun setupGraph() {
        binding.abnMain.setOnItemSelectedListener nav@{ menu ->
            viewModel.onTabItemSelected(
                MainTabType.getTypeById(menu.itemId) ?: return@nav false
            )
        }
    }

    private fun setupObserver() {
        observeLiveData(viewModel.tabContentType, ::openTab)
    }

    private fun openTab(selectedTab: MainTabType) {
        val transaction = childFragmentManager.beginTransaction()
        prevTab?.fragment?.let { fragment ->
            transaction
                .detach(fragment)
                .commitNow()
        }
        prevTab = selectedTab
        val tabFragment = selectedTab.fragment

        if (tabFragment.isNotNull()) {
            transaction.attach(tabFragment)
        } else {
            transaction.add(
                R.id.nav_host_fragment_container_main,
                selectedTab.createFragment(),
                selectedTab.name
            )
        }
        transaction.commitNow()
    }

    private fun MainTabType.createFragment() = SafeNavHostFragment.createHost(navigationRes)
}
