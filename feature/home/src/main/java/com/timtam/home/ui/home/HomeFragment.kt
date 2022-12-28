package com.timtam.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.common_android.extension.i
import com.timtam.common_android.extension.toast
import com.timtam.common_android.extension.viewLifecycleLazy
import com.timtam.feature_helper.extension.inspect
import com.timtam.feature_helper.extension.observeLiveData
import com.timtam.feature_helper.extension.observeLiveEvent
import com.timtam.home.databinding.FragmentHomeBinding
import com.timtam.home.type.HomeViewType
import com.timtam.home.ui.genre.adapter.MovieGenreListener
import com.timtam.home.ui.genre.payload.HomeGenrePayload
import com.timtam.home.ui.header.listener.HomeHeaderListener
import com.timtam.home.ui.home.adapter.HomeAdapter
import com.timtam.home.ui.nowplaying.adapter.MovieNowPlayingListener
import com.timtam.home.ui.nowplaying.payload.HomeMovieNowPlayingPayload
import com.timtam.home.ui.toprated.adapter.MovieTopRatedListener
import com.timtam.home.ui.toprated.payload.HomeMovieTopRatedPayload
import com.timtam.uikit.extension.detachFromAdapter
import com.timtam.uikit.extension.gone
import com.timtam.uikit.extension.visible
import com.timtam.uikit.extension.weaken
import com.timtam.uikit.recyclerview.base.RecyclerViewInitiator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    LifecycleFragment<FragmentHomeBinding>(),
    FragmentRetainable by FragmentRetainer() {

    private val viewModel: HomeViewModel by viewModels()

    private var homeAdapter: HomeAdapter? = null

    private val homeHeaderListener by viewLifecycleLazy {
        object : HomeHeaderListener {
            override fun onArrangeClick() {
                toast { "OPEN ARRANGE" }
            }

            override fun onSearchBoxClick() {
                toast { "OPEN SEARCH" }
            }
        }
    }

    private val movieGenreListener by viewLifecycleLazy {
        object : MovieGenreListener {
            override fun onMoreClick() {
                toast { "GENRE MORE CLICKED" }
            }

            override fun onGenreClick(genreId: Int) {
                toast { "GENRE ID => $genreId" }
            }
        }
    }

    private val movieNowPlayingListener by viewLifecycleLazy {
        object : MovieNowPlayingListener {
            override fun onMoreClick() {
                toast { "NOW PLAYING MORE CLICKED" }
            }

            override fun onMovieClick(movieId: Int) {
                toast { "NOW PLAYING ID => $movieId" }
            }
        }
    }

    private val movieTopRatedListener by viewLifecycleLazy {
        object : MovieTopRatedListener {
            override fun onMoreClick() {
                toast { "TOP RATED MORE CLICKED" }
            }

            override fun onMovieClick(movieId: Int) {
                toast { "TOP RATED ID => $movieId" }
            }
        }
    }

    private val recyclerViewInitiator by lazy {
        RecyclerViewInitiator.Builder<HomeAdapter>()
            .withRecyclerView(binding.rvHomeContent.weaken())
            .withListener {
                registerListener(
                    homeHeaderListener = homeHeaderListener,
                    movieGenreListener = movieGenreListener,
                    movieNowPlayingListener = movieNowPlayingListener,
                    movieTopRatedListener = movieTopRatedListener
                )
            }
            .withAdapter {
                HomeAdapter().also { homeAdapter = it }
            }
            .onAttachedAdapter {
                homeAdapter?.submitList(HomeViewType.defaultOrder)
            }
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return oneTimeRenderer {
            _binding = FragmentHomeBinding.inflate(inflater, container, false)
            binding.root
        }.apply { _binding?.lifecycleOwner = viewLifecycleOwner }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupEventObserver()
        setupDataObserver()
    }

    override fun onDestroyView() {
        homeAdapter?.releaseResource()
        super.onDestroyView()
    }

    override fun onDestroy() {
        _binding?.rvHomeContent?.detachFromAdapter()
        super.onDestroy()
    }

    override fun onHandleData() {
        super.onHandleData()
        viewModel.fetchMovieGenres(HOME_MOVIE_GENRE_LIMIT) {
            viewModel.fetchSnipsNowPlaying(it, HOME_MOVIE_NOW_PLAYING_LIMIT)
        }
        viewModel.fetchSnipsTopRated(HOME_MOVIE_TOP_RATED_LIMIT)
    }

    private fun setupDataObserver() {
        observeLiveData(viewModel.mainLoading) { isLoading ->
            if (isLoading) {
                startMainLoading()
            } else {
                stopMainLoading()
            }
        }

        observeLiveData(viewModel.snipsNowPlayingLoading) { isLoading ->
            i { "TIMUR now playing loading: $isLoading" }
        }

        observeLiveData(viewModel.snipsTopRatedLoading) { isLoading ->
            i { "TIMUR top rated loading: $isLoading" }
        }

        observeLiveData(viewModel.movieSnipsNowPlaying) { movies ->
            homeAdapter?.enqueueAdapterPayload(
                HomeViewType.defaultOrder.indexOf(HomeViewType.NOW_PLAYING),
                HomeMovieNowPlayingPayload.ShowData(movies)
            )
        }

        observeLiveData(viewModel.movieSnipsTopRated) { movies ->
            homeAdapter?.enqueueAdapterPayload(
                HomeViewType.defaultOrder.indexOf(HomeViewType.TOP_RATED),
                HomeMovieTopRatedPayload.ShowData(movies)
            )
        }

        observeLiveData(viewModel.movieGenres) { genres ->
            homeAdapter?.enqueueAdapterPayload(
                HomeViewType.defaultOrder.indexOf(HomeViewType.GENRE),
                HomeGenrePayload.ShowData(genres)
            )
        }
    }

    private fun setupEventObserver() {
        observeLiveEvent(viewModel.errorType) { type ->
            type.inspect(
                keepData = {
                    toast { it.message.orEmpty() }
                },
                errorUi = {
                    when (it.key) {
                        HomeViewType.GENRE -> {
                            homeAdapter?.enqueueAdapterPayload(
                                HomeViewType.defaultOrder.indexOf(HomeViewType.GENRE),
                                HomeGenrePayload.ShowError
                            )
                        }
                        HomeViewType.NOW_PLAYING -> {
                            homeAdapter?.enqueueAdapterPayload(
                                HomeViewType.defaultOrder.indexOf(HomeViewType.NOW_PLAYING),
                                HomeMovieNowPlayingPayload.ShowError
                            )
                        }
                        HomeViewType.TOP_RATED -> {
                            homeAdapter?.enqueueAdapterPayload(
                                HomeViewType.defaultOrder.indexOf(HomeViewType.TOP_RATED),
                                HomeMovieTopRatedPayload.ShowError
                            )
                        }
                        else -> Unit
                    }
                },
                emptyUi = {
                    when (it.key) {
                        HomeViewType.NOW_PLAYING -> {
                            homeAdapter?.enqueueAdapterPayload(
                                HomeViewType.defaultOrder.indexOf(HomeViewType.NOW_PLAYING),
                                HomeMovieNowPlayingPayload.ShowEmpty
                            )
                        }
                        HomeViewType.TOP_RATED -> {
                            homeAdapter?.enqueueAdapterPayload(
                                HomeViewType.defaultOrder.indexOf(HomeViewType.TOP_RATED),
                                HomeMovieTopRatedPayload.ShowEmpty
                            )
                        }
                        else -> Unit
                    }
                }
            )
        }
    }

    private fun setupRecyclerView() = recyclerViewInitiator.initialize()

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
        private const val HOME_MOVIE_TOP_RATED_LIMIT = 5
        private const val HOME_MOVIE_GENRE_LIMIT = 6
    }
}
