package com.yds.smartweather

import android.app.Application
import android.content.Context

/**
 * @author YDS
 * @date 2021/2/9
 * @discribe
 */
class MyApplication : Application() {

    companion object {
        lateinit var context: Context
        const val TOKEN="gTZoKB5XwQjnqIOI"
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
    }

}