package com.timtam.common.extension

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun Activity.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(this, msg(), duration).show()
}

fun Fragment.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(context, msg(), duration).show()
}
