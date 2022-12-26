package com.timtam.feature_helper.delegation

import com.timtam.feature_helper.extension.LiveEvent
import com.timtam.feature_helper.type.ErrorDisplayType
import kotlinx.coroutines.CoroutineScope

interface DisplayableErrorDelegate<Arg> {

    val errorType: LiveEvent<ErrorDisplayType<Arg>>

    fun displayError(viewModelScope: CoroutineScope, type: () -> ErrorDisplayType<Arg>)
}
