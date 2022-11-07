package com.timtam.moview.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isShowSplash = MutableStateFlow(true)
    val isShowSplash get() = _isShowSplash.asStateFlow()

    fun setSplashTime(timeMillis: Long) = viewModelScope.launch {
        delay(timeMillis)
        _isShowSplash.value = false
    }
}
