package com.timtam.home.ui.base

open class StatefulArg {
    var isError: Boolean = false
    var isEmpty: Boolean = false
    var isLoading: Boolean = false
        set(value) {
            field = value
            if (value) {
                isError = false
                isEmpty = false
            }
        }
}
