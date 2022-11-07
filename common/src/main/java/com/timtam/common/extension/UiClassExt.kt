package com.timtam.common.extension

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(this, msg(), duration).show()
}

fun Fragment.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(context, msg(), duration).show()
}

fun Fragment.activeActivity(lambda: (Activity) -> Unit) {
    activity?.let(lambda) ?: e { "${javaClass.simpleName} not attached to activity" }
}
