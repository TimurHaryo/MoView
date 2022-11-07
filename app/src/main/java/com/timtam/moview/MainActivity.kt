package com.timtam.moview

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.timtam.common.abstraction.BindingActivity
import com.timtam.moview.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding(DataBindingUtil.setContentView(this, R.layout.activity_main))
    }
}
