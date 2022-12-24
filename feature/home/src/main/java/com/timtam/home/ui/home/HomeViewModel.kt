package com.timtam.home.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.common_android.extension.i
import com.timtam.usecase.movie.GetMovieSnipsNowPlayingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMovieSnipsNowPlaying: GetMovieSnipsNowPlayingUseCase
) : ViewModel() {

    fun getSnipsNowPlaying(limit: Int) = viewModelScope.launch {
        getMovieSnipsNowPlaying(limit).collect {
            i { "Movie State: $it" }
        }
    }
}
