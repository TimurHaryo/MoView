package com.timtam.common.extension

import android.util.Log
import com.timtam.common.BuildConfig

inline fun <reified T> T.i(crossinline log: () -> String) = onDebug {
    this?.let { Log.i(it::class.java.simpleName, log()) }
}

inline fun <reified T> T.e(crossinline log: () -> String) = onDebug {
    this?.let { Log.e(it::class.java.simpleName, log()) }
}

fun onDebug(block: () -> Unit) {
    if (BuildConfig.DEBUG) block()
}
