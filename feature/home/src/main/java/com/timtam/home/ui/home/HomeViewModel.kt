package com.timtam.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.feature_item.movie.MovieSnipsNowPlayingItem
import com.timtam.usecase.movie.GetMovieSnipsNowPlayingUseCase
import com.timtam.usecase.movie.state.MovieSnipsNowPlayingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieSnipsNowPlaying: GetMovieSnipsNowPlayingUseCase
) : ViewModel() {

    private val _snipsNowPlayingLoading = MutableLiveData<Boolean>()
    val snipsNowPlayingLoading: LiveData<Boolean> get() = _snipsNowPlayingLoading

    private val _movieSnipsNowPlaying = MutableLiveData<List<MovieSnipsNowPlayingItem>>()
    val movieSnipsNowPlaying: LiveData<List<MovieSnipsNowPlayingItem>> get() = _movieSnipsNowPlaying

    fun getSnipsNowPlaying(limit: Int) = viewModelScope.launch {
        getMovieSnipsNowPlaying(limit).collect { state ->
            when (state) {
                is MovieSnipsNowPlayingState.Empty -> {
                    _snipsNowPlayingLoading.value = false
                }
                is MovieSnipsNowPlayingState.Error -> {
                    _snipsNowPlayingLoading.value = false
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
