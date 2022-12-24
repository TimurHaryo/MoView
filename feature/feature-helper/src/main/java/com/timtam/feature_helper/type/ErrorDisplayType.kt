package com.timtam.feature_helper.type

sealed class ErrorDisplayType<Arg> {
    data class KeepData<Key>(val message: String?) : ErrorDisplayType<Key>()

    data class ErrorUi<Key>(val key: Key? = null) : ErrorDisplayType<Key>()

    data class EmptyUi<Key>(val key: Key? = null) : ErrorDisplayType<Key>()
}
