package com.timtam.remote_interactor.interceptor

import com.timtam.remote_interactor.util.Constant
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url
            .newBuilder()
            .addQueryParameter("api_key", Constant.API_KEY)
            .build()

        return chain.proceed(
            originalRequest.newBuilder()
                .url(url)
                .build()
        )
    }
}
