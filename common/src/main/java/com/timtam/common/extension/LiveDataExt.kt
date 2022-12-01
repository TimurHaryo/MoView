package com.timtam.common.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

inline fun <T> Fragment.observeValue(source: LiveData<T>, crossinline lambda: (T) -> Unit) {
    source.observe(viewLifecycleOwner) { data ->
        data?.let(lambda)
    }
}
