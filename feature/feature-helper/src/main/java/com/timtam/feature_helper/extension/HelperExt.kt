package com.timtam.feature_helper.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timtam.feature_helper.type.ErrorDisplayType
import kotlinx.coroutines.CoroutineScope

interface ViewModelSegment

val ViewModelSegment.viewModelSegmentScope: CoroutineScope
    get() {
        return if (this is ViewModel) {
            this.viewModelScope
        } else {
            throw IllegalStateException(
                "ViewModelSegment must be implemented in a class that extends ViewModel"
            )
        }
    }

inline fun <T> ErrorDisplayType<T>.inspect(
    crossinline keepData: ((ErrorDisplayType.KeepData<T>) -> Unit) = {},
    crossinline errorUi: ((ErrorDisplayType.ErrorUi<T>) -> Unit) = {},
    crossinline emptyUi: ((ErrorDisplayType.EmptyUi<T>) -> Unit) = {}
) {
    when (val type = this) {
        is ErrorDisplayType.ErrorUi -> errorUi(type)
        is ErrorDisplayType.KeepData -> keepData(type)
        is ErrorDisplayType.EmptyUi -> emptyUi(type)
    }
}
