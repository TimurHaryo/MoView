package com.timtam.common_kotlin.extension

import retrofit2.Response

fun <T> Response<T>.isTotallySuccess(): Boolean {
    return this.isSuccessful && this.body() != null
}

fun <T> Response<T>.hasEmptyBody(): Boolean {
    return this.isSuccessful && this.body() == null
}
