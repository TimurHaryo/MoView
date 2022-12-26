package com.timtam.feature_helper.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.timtam.feature_helper.wrapper.Event

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

typealias LiveEvent<T> = LiveData<Event<T>>

inline fun <T> Fragment.observeLiveData(source: LiveData<T>, crossinline lambda: (T) -> Unit) {
    source.observe(viewLifecycleOwner) { data ->
        data?.let(lambda)
    }
}

inline fun <T> Fragment.observeLiveEvent(source: LiveEvent<T>, crossinline lambda: (T) -> Unit) {
    source.observe(viewLifecycleOwner) { event ->
        event?.getContentIfNotHandled()?.let(lambda)
    }
}

fun <T> T.toEvent() where T : Any = Event(this)
