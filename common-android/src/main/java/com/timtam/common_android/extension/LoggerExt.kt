package com.timtam.common_android.extension

import com.timtam.common_android.BuildConfig
import timber.log.Timber

inline fun i(crossinline log: () -> String) {
    Timber.i(log())
}

inline fun e(crossinline log: () -> String) {
    Timber.e(log())
}

inline fun onDebug(crossinline block: () -> Unit) {
    if (BuildConfig.DEBUG) block()
}
