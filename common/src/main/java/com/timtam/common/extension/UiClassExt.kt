package com.timtam.common.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.timtam.common.R
import com.timtam.common.util.ViewLifecycleLazy
import java.lang.ref.WeakReference
import java.util.Calendar

fun Activity.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(this, msg(), duration).show()
}

fun Fragment.toast(duration: Int = Toast.LENGTH_SHORT, msg: () -> String) {
    Toast.makeText(context, msg(), duration).show()
}

fun Fragment.activeActivity(lambda: (Activity) -> Unit) {
    activity?.let(lambda) ?: e { "${javaClass.simpleName} not attached to activity" }
}

fun Fragment.weaken() = WeakReference(this)

fun <T : Any> Fragment.viewLifecycleLazy(initializer: () -> T): Lazy<T> =
    ViewLifecycleLazy(this.weaken(), initializer)

fun Context?.greeting(): String {
    this ?: return ""

    val cal = Calendar.getInstance()
    return getString(
        when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> R.string.text_greeting_morning
            in 12..15 -> R.string.text_greeting_afternoon
            in 16..23 -> R.string.text_greeting_evening
            else -> R.string.text_greeting_night
        }
    )
}
