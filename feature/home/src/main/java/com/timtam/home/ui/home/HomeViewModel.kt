package com.timtam.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.feature_helper.delegation.DisplayableErrorDelegate
import com.timtam.feature_helper.delegation.DisplayableErrorDelegateImpl
import com.timtam.feature_helper.extension.LiveEvent
import com.timtam.feature_helper.extension.MutableLiveEvent
import com.timtam.feature_helper.extension.toEvent
import com.timtam.feature_helper.type.ErrorDisplayType
import com.timtam.feature_item.genre.GenreHomeItem
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.feature_item.movie.MovieSnipsTopRatedItem
import com.timtam.home.type.HomeViewType
import com.timtam.usecase.genre.GetMovieSnipsGenreUseCase
import com.timtam.usecase.genre.state.GenreState
import com.timtam.usecase.movie.GetMovieSnipsNowPlayingUseCase
import com.timtam.usecase.movie.GetMovieSnipsTopRatedUseCase
import com.timtam.usecase.movie.state.MovieSnipsNowPlayingState
import com.timtam.usecase.movie.state.MovieSnipsTopRatedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieSnipsNowPlaying: GetMovieSnipsNowPlayingUseCase,
    private val getMovieSnipsTopRated: GetMovieSnipsTopRatedUseCase,
    private val getMovieSnipsGenre: GetMovieSnipsGenreUseCase
) :
    ViewModel(),
    DisplayableErrorDelegate<HomeViewType> by DisplayableErrorDelegateImpl() {

    val cachedMovieGenres get() = _movieGenres.value?.peekContent().orEmpty()

    private val _mainLoading = MutableLiveData<Boolean>()
    val mainLoading: LiveData<Boolean> get() = _mainLoading

    private val _snipsNowPlayingLoading = MutableLiveData<Boolean>()
    val snipsNowPlayingLoading: LiveData<Boolean> get() = _snipsNowPlayingLoading

    private val _snipsTopRatedLoading = MutableLiveData<Boolean>()
    val snipsTopRatedLoading: LiveData<Boolean> get() = _snipsTopRatedLoading

    private val _movieSnipsNowPlaying = MutableLiveEvent<List<MovieSnipsNowPlayingItem>>()
    val movieSnipsNowPlaying: LiveEvent<List<MovieSnipsNowPlayingItem>> get() = _movieSnipsNowPlaying

    private val _movieSnipsTopRated = MutableLiveEvent<List<MovieSnipsTopRatedItem>>()
    val movieSnipsTopRated: LiveEvent<List<MovieSnipsTopRatedItem>> get() = _movieSnipsTopRated

    private val _movieGenres = MutableLiveEvent<List<GenreHomeItem>>()
    val movieGenres: LiveEvent<List<GenreHomeItem>> get() = _movieGenres

    private val isTopRatedEmpty get() = _movieSnipsTopRated.value?.peekContent().isNullOrEmpty()
    private val isNowPlayingEmpty get() = _movieSnipsNowPlaying.value?.peekContent().isNullOrEmpty()
    private val isGenreEmpty get() = _movieGenres.value?.peekContent().isNullOrEmpty()

    fun fetchMovieGenres(limit: Int) = viewModelScope.launch {
        getMovieSnipsGenre().collect { state ->
            when (state) {
                is GenreState.Error -> {
                    _mainLoading.value = false
                    displayError(viewModelScope) {
                        if (isGenreEmpty) {
                            ErrorDisplayType.ErrorUi(HomeViewType.GENRE)
                        } else {
                            ErrorDisplayType.KeepData(state.error.message)
                        }
                    }
                }
                is GenreState.Loading -> {
                    _mainLoading.value = true
                }
                is GenreState.Success -> {
                    _mainLoading.value = false
                    _movieGenres.value = state.data.take(limit).toEvent()
                }
            }
        }
    }

    fun fetchSnipsNowPlaying(genres: List<GenreHomeItem>, limit: Int) = viewModelScope.launch {
        getMovieSnipsNowPlaying(genres, limit).collect { state ->
            when (state) {
                is MovieSnipsNowPlayingState.Empty -> {
                    _snipsNowPlayingLoading.value = false
                    displayError(viewModelScope) {
                        ErrorDisplayType.EmptyUi(HomeViewType.NOW_PLAYING)
                    }
                }
                is MovieSnipsNowPlayingState.Error -> {
                    _snipsNowPlayingLoading.value = false
                    displayError(viewModelScope) {
                        if (isNowPlayingEmpty) {
                            ErrorDisplayType.ErrorUi(HomeViewType.NOW_PLAYING)
                        } else {
                            ErrorDisplayType.KeepData(state.error.message)
                        }
                    }
                }
                is MovieSnipsNowPlayingState.Loading -> {
                    _snipsNowPlayingLoading.value = true
                }
                is MovieSnipsNowPlayingState.Success -> {
                    _snipsNowPlayingLoading.value = false
                    _movieSnipsNowPlaying.value = state.data.toEvent()
                }
            }
        }
    }

    fun fetchSnipsTopRated(limit: Int) = viewModelScope.launch {
        getMovieSnipsTopRated(limit).collect { state ->
            when (state) {
                is MovieSnipsTopRatedState.Empty -> {
                    _snipsTopRatedLoading.value = false
                    displayError(viewModelScope) {
                        ErrorDisplayType.EmptyUi(HomeViewType.TOP_RATED)
                    }
                }
                is MovieSnipsTopRatedState.Error -> {
                    _snipsTopRatedLoading.value = false
                    displayError(viewModelScope) {
                        if (isTopRatedEmpty) {
                            ErrorDisplayType.ErrorUi(HomeViewType.TOP_RATED)
                        } else {
                            ErrorDisplayType.KeepData(state.error.message)
                        }
                    }
                }
                is MovieSnipsTopRatedState.Loading -> {
                    _snipsTopRatedLoading.value = true
                }
                is MovieSnipsTopRatedState.Success -> {
                    _snipsTopRatedLoading.value = false
                    _movieSnipsTopRated.value = state.data.toEvent()
                }
            }
        }
    }
}
