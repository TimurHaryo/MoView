package com.timtam.feature_helper.extension

import com.timtam.feature_helper.type.ErrorDisplayType

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
