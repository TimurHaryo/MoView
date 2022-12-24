package com.timtam.feature_helper.delegation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timtam.feature_helper.extension.viewModelSegmentScope
import com.timtam.feature_helper.type.ErrorDisplayType
import kotlinx.coroutines.launch

class DisplayableErrorDelegateImpl<Arg> : DisplayableErrorDelegate<Arg> {
    private val _errorType = MutableLiveData<ErrorDisplayType<Arg>>()
    override val errorType: LiveData<ErrorDisplayType<Arg>> get() = _errorType

    override fun displayError(type: ErrorDisplayType<Arg>) {
        viewModelSegmentScope.launch {
            _errorType.value = type
        }
    }
}
