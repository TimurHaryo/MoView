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
import com.timtam.home.ui.type.HomeViewType
import com.timtam.uikit.extension.gone
import com.timtam.uikit.extension.visible
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
            setupRecyclerView()
        }
        setupObserver()
    }

    override fun onHandleData() {
        super.onHandleData()
        viewModel.fetchMovieGenres(HOME_MOVIE_GENRE_LIMIT) {
            viewModel.fetchSnipsNowPlaying(it, HOME_MOVIE_NOW_PLAYING_LIMIT)
        }
    }

    private fun setupObserver() {
        observeValue(viewModel.mainLoading) { isLoading ->
            if (isLoading) {
                startMainLoading()
            } else {
                stopMainLoading()
            }
        }

        observeValue(viewModel.snipsNowPlayingLoading) { isLoading ->
            i { "TIMUR now playing loading: $isLoading" }
        }

        observeValue(viewModel.movieSnipsNowPlaying) { movies ->
            i { "TIMUR now playing movies title: ${movies.map { it.title }}" }
            i { "TIMUR now playing movies genres: ${movies.map { it.genres }}" }
        }

        observeValue(viewModel.movieGenres) { genres ->
            i { "TIMUR movie genres: ${genres.map { it.type }}" }
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

    private fun startMainLoading() = with(binding) {
        sflHomeMain.visible()
        sflHomeMain.startShimmer()

        rvHomeContent.gone()
    }

    private fun stopMainLoading() = with(binding) {
        sflHomeMain.stopShimmer()
        sflHomeMain.gone()

        rvHomeContent.visible()
    }

    companion object {
        private const val HOME_MOVIE_NOW_PLAYING_LIMIT = 5
        private const val HOME_MOVIE_GENRE_LIMIT = 6
    }
}
