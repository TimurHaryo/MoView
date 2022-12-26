package com.timtam.feature_helper.delegation

import com.timtam.feature_helper.extension.LiveEvent
import com.timtam.feature_helper.extension.MutableLiveEvent
import com.timtam.feature_helper.extension.toEvent
import com.timtam.feature_helper.type.ErrorDisplayType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DisplayableErrorDelegateImpl<Arg : Any> : DisplayableErrorDelegate<Arg> {

    private val _errorType = MutableLiveEvent<ErrorDisplayType<Arg>>()
    override val errorType: LiveEvent<ErrorDisplayType<Arg>> get() = _errorType

    override fun displayError(viewModelScope: CoroutineScope, type: () -> ErrorDisplayType<Arg>) {
        viewModelScope.launch { _errorType.value = type().toEvent() }
    }
}
