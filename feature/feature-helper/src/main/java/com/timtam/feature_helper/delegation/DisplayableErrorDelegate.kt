package com.timtam.feature_helper.delegation

import androidx.lifecycle.LiveData
import com.timtam.feature_helper.extension.ViewModelSegment
import com.timtam.feature_helper.type.ErrorDisplayType

interface DisplayableErrorDelegate<Arg> : ViewModelSegment {

    val errorType: LiveData<ErrorDisplayType<Arg>>

    fun displayError(type: ErrorDisplayType<Arg>)
}
