package com.timtam.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.feature_helper.delegation.DisplayableErrorDelegate
import com.timtam.feature_helper.delegation.DisplayableErrorDelegateImpl
import com.timtam.feature_helper.type.ErrorDisplayType
import com.timtam.feature_item.genre.GenreItem
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.home.ui.type.HomeViewType
import com.timtam.usecase.genre.GetMovieGenreUseCase
import com.timtam.usecase.genre.state.GenreState
import com.timtam.usecase.movie.GetMovieSnipsNowPlayingUseCase
import com.timtam.usecase.movie.state.MovieSnipsNowPlayingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieSnipsNowPlaying: GetMovieSnipsNowPlayingUseCase,
    private val getMovieGenres: GetMovieGenreUseCase
) :
    ViewModel(),
    DisplayableErrorDelegate<HomeViewType> by DisplayableErrorDelegateImpl() {

    private val _mainLoading = MutableLiveData<Boolean>()
    val mainLoading: LiveData<Boolean> get() = _mainLoading

    private val _snipsNowPlayingLoading = MutableLiveData<Boolean>()
    val snipsNowPlayingLoading: LiveData<Boolean> get() = _snipsNowPlayingLoading

    private val _movieSnipsNowPlaying = MutableLiveData<List<MovieSnipsNowPlayingItem>>()
    val movieSnipsNowPlaying: LiveData<List<MovieSnipsNowPlayingItem>> get() = _movieSnipsNowPlaying

    private val _movieGenres = MutableLiveData<List<GenreItem>>()
    val movieGenres: LiveData<List<GenreItem>> get() = _movieGenres

    fun fetchMovieGenres(
        limit: Int,
        doAfter: (List<GenreItem>) -> Unit
    ) = viewModelScope.launch {
        getMovieGenres().collect { state ->
            when (state) {
                is GenreState.Error -> {
                    _mainLoading.value = false
                    displayError(
                        if (_movieGenres.value.isNullOrEmpty()) {
                            ErrorDisplayType.ErrorUi(HomeViewType.GENRE)
                        } else {
                            ErrorDisplayType.KeepData(state.error.message)
                        }
                    )
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

    fun fetchSnipsNowPlaying(genres: List<GenreItem>, limit: Int) = viewModelScope.launch {
        getMovieSnipsNowPlaying(genres, limit).collect { state ->
            when (state) {
                is MovieSnipsNowPlayingState.Empty -> {
                    _snipsNowPlayingLoading.value = false
                    displayError(ErrorDisplayType.EmptyUi(HomeViewType.NOW_PLAYING))
                }
                is MovieSnipsNowPlayingState.Error -> {
                    _snipsNowPlayingLoading.value = false
                    displayError(
                        if (_movieSnipsNowPlaying.value.isNullOrEmpty()) {
                            ErrorDisplayType.ErrorUi(HomeViewType.NOW_PLAYING)
                        } else {
                            ErrorDisplayType.KeepData(state.error.message)
                        }
                    )
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
}
