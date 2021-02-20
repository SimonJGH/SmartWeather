package com.yds.smartweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yds.smartweather.util.AppLifeObserve

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        lifecycle.addObserver(AppLifeObserve(lifecycle))
    }
}