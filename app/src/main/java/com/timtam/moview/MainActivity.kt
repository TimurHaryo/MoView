package com.timtam.moview

import android.os.Bundle
import com.timtam.common.abstraction.BindingActivity
import com.timtam.common.delegation.inflater.AsyncInflater
import com.timtam.common.delegation.inflater.AsyncInflaterImpl
import com.timtam.moview.databinding.ActivityMainBinding

class MainActivity :
    BindingActivity<ActivityMainBinding>(),
    AsyncInflater by AsyncInflaterImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate(R.layout.activity_main) { view ->
            setBinding(ActivityMainBinding.bind(view))
            setContentView(binding.root)
        }
    }
}
