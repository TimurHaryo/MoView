package com.timtam.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.feature_helper.delegation.DisplayableErrorDelegate
import com.timtam.feature_helper.delegation.DisplayableErrorDelegateImpl
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

    private val _mainLoading = MutableLiveData<Boolean>()
    val mainLoading: LiveData<Boolean> get() = _mainLoading

    private val _snipsNowPlayingLoading = MutableLiveData<Boolean>()
    val snipsNowPlayingLoading: LiveData<Boolean> get() = _snipsNowPlayingLoading

    private val _snipsTopRatedLoading = MutableLiveData<Boolean>()
    val snipsTopRatedLoading: LiveData<Boolean> get() = _snipsTopRatedLoading

    private val _movieSnipsNowPlaying = MutableLiveData<List<MovieSnipsNowPlayingItem>>()
    val movieSnipsNowPlaying: LiveData<List<MovieSnipsNowPlayingItem>> get() = _movieSnipsNowPlaying

    private val _movieSnipsTopRated = MutableLiveData<List<MovieSnipsTopRatedItem>>()
    val movieSnipsTopRated: LiveData<List<MovieSnipsTopRatedItem>> get() = _movieSnipsTopRated

    private val _movieGenres = MutableLiveData<List<GenreHomeItem>>()
    val movieGenres: LiveData<List<GenreHomeItem>> get() = _movieGenres

    fun fetchMovieGenres(
        limit: Int,
        doAfter: (List<GenreHomeItem>) -> Unit
    ) = viewModelScope.launch {
        getMovieSnipsGenre().collect { state ->
            when (state) {
                is GenreState.Error -> {
                    _mainLoading.value = false
                    displayError(viewModelScope) {
                        if (_movieGenres.value.isNullOrEmpty()) {
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
                    _movieGenres.value = state.data.take(limit)
                    doAfter(state.data)
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
                        if (_movieSnipsNowPlaying.value.isNullOrEmpty()) {
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
                    _movieSnipsNowPlaying.value = state.data
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
                        if (_movieSnipsTopRated.value.isNullOrEmpty()) {
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
                    _movieSnipsTopRated.value = state.data
                }
            }
        }
    }
}
