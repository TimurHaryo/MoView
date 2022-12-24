package com.timtam.moview

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.timtam.common_android.extension.onDebug
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        onDebug { Timber.plant(Timber.DebugTree()) }

        AndroidThreeTen.init(this)
    }
}
