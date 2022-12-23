package com.timtam.local_interactor.util

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ConverterUtil {

    fun <T> resolveType(): Type? = object : TypeToken<T>() {}.type
}

