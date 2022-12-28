package com.timtam.common_kotlin.extension

import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

fun onDayChanged(lastOpenTime: String, onChange: () -> Unit) {
    try {
        if (Instant.parse(lastOpenTime).isBefore(Instant.now())) onChange()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

fun reformatDate(dateString: String, newFormat: String = "dd MMMM yyyy"): String =
    DateTimeFormatter
        .ofPattern(newFormat, Locale.getDefault())
        .format(LocalDate.parse(dateString))
