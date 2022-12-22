package com.timtam.remote_interactor.di

import com.google.gson.Gson
import com.timtam.remote_interactor.BuildConfig
import com.timtam.remote_interactor.interceptor.AuthInterceptor
import com.timtam.remote_interactor.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(okHttpClient)
        .build()
}
