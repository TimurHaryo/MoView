package com.timtam.initial

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.timtam.common.abstraction.BindingActivity
import com.timtam.initial.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.schedule

class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
        setBinding(DataBindingUtil.setContentView(this, R.layout.activity_main))
    }

    private fun showSplashScreen() {
        val splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }

        Timer().schedule(2000L) {
            splashScreen.setKeepOnScreenCondition { false }
        }
    }
}
