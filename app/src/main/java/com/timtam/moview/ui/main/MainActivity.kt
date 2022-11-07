package com.timtam.moview.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.timtam.common.abstraction.BindingActivity
import com.timtam.common.extension.isNotZero
import com.timtam.moview.R
import com.timtam.moview.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        showSplashScreen()
        super.onCreate(savedInstanceState)
        setBinding(DataBindingUtil.setContentView(this, R.layout.activity_main))
    }

    private fun showSplashScreen() {
        val duration = 2000L
        if (duration.isNotZero()) {
            viewModel.setSplashTime(duration)
        }
        installSplashScreen().apply {
            if (duration.isNotZero()) {
                setKeepOnScreenCondition { viewModel.isShowSplash.value }
            }
        }
    }
}
