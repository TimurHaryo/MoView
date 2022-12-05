package com.timtam.common.extension

import org.threeten.bp.Instant

fun onDayChanged(lastOpenTime: String, onChange: () -> Unit) {
    try {
        if (Instant.parse(lastOpenTime).isBefore(Instant.now())) onChange()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}
