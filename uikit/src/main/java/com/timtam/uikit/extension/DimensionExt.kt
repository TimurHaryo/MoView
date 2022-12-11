package com.timtam.uikit.extension

import android.content.Context
import kotlin.math.round

fun Context.dpToPx(value: Int) =
    round(value.toFloat() * resources.displayMetrics.density)

fun Context.dpToPx(value: Float) =
    round(value * resources.displayMetrics.density)

fun Context.pxToDp(value: Int) =
    round(value.toFloat() / resources.displayMetrics.density)

fun Context.pxToDp(value: Float) =
    round(value / resources.displayMetrics.density)
