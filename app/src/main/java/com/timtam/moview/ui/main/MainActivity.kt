package com.timtam.moview.ui.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.timtam.common_android.abstraction.BindingActivity
import com.timtam.moview.R
import com.timtam.moview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import kotlin.concurrent.schedule
import com.timtam.navigation.R as navR

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
        setBinding(DataBindingUtil.setContentView(this, R.layout.activity_main))
        setupGraph()
    }

    private fun showSplashScreen() {
        val splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }

        Timer().schedule(SPLASH_DURATION) {
            splashScreen.setKeepOnScreenCondition { false }
        }
    }

    private fun setupGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        val graph =
            navHostFragment.navController.navInflater.inflate(navR.navigation.initial_flow)

        navHostFragment.navController.graph = graph
    }

    companion object {
        private const val SPLASH_DURATION = 2000L
    }
}
