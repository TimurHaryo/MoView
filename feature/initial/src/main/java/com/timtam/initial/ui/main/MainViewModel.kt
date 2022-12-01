package com.timtam.initial.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timtam.initial.model.type.MainTabType

class MainViewModel : ViewModel() {
    private val _tabContentType = MutableLiveData(MainTabType.HOME)
    val tabContentType: LiveData<MainTabType> get() = _tabContentType

    fun onTabItemSelected(tab: MainTabType): Boolean {
        if (tab != _tabContentType.value) _tabContentType.value = tab
        return true
    }
}
