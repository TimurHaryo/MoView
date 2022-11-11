package com.timtam.initial

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.timtam.common.abstraction.BindingActivity
import com.timtam.initial.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.schedule
import com.timtam.navigation.R as navR

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

        Timer().schedule(2000L) {
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
}
