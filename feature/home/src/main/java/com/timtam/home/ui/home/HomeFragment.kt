package com.timtam.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.common_android.extension.e
import com.timtam.common_android.extension.i
import com.timtam.common_android.extension.observeValue
import com.timtam.common_android.extension.toast
import com.timtam.common_android.extension.viewLifecycleLazy
import com.timtam.feature_helper.extension.inspect
import com.timtam.home.databinding.FragmentHomeBinding
import com.timtam.home.ui.home.adapter.HomeAdapter
import com.timtam.home.ui.model.HomeViewType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    LifecycleFragment<FragmentHomeBinding>(),
    FragmentRetainable by FragmentRetainer() {

    private val viewModel: HomeViewModel by viewModels()
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
            binding.btnTestPeekNetwork.setOnClickListener {
                viewModel.getSnipsNowPlaying(MOVIE_NOW_PLAYING_LIMIT)
            }
            setupRecyclerView()
        }
        setupObserver()
    }

    override fun onHandleData() {
        super.onHandleData()
        viewModel.getSnipsNowPlaying(MOVIE_NOW_PLAYING_LIMIT)
    }

    private fun setupObserver() {
        observeValue(viewModel.snipsNowPlayingLoading) { isLoading ->
            i { "TIMUR now playing loading: $isLoading" }
        }

        observeValue(viewModel.movieSnipsNowPlaying) { movies ->
            i { "TIMUR now playing movies: ${movies.map { it.title }}" }
        }

        observeValue(viewModel.errorType) { type ->
            type.inspect(
                keepData = {
                    toast { it.message.orEmpty() }
                },
                errorUi = {
                    when (it.key) {
                        HomeViewType.GENRE -> e { "SHOW GENRE ERROR UI" }
                        HomeViewType.NOW_PLAYING -> e { "SHOW NOW_PLAYING ERROR UI" }
                        HomeViewType.TOP_RATED -> e { "SHOW TOP_RATED ERROR UI" }
                        else -> Unit
                    }
                },
                emptyUi = {
                    when (it.key) {
                        HomeViewType.NOW_PLAYING -> e { "SHOW NOW_PLAYING EMPTY UI" }
                        HomeViewType.TOP_RATED -> e { "SHOW TOP_RATED EMPTY UI" }
                        else -> Unit
                    }
                }
            )
        }
    }

    private fun setupRecyclerView() = with(binding.rvHomeContent) {
        adapter = homeAdapter.apply {
            submitList(HomeViewType.defaultOrder)
        }
    }

    companion object {
        private const val MOVIE_NOW_PLAYING_LIMIT = 5
    }
}
