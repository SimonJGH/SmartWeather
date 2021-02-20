package com.yds.smartweather

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log

/**
 * @author YDS
 * @date 2021/2/9
 * @discribe
 */
class MyApplication : Application() {

    companion object {
        lateinit var context: Context
        const val TOKEN = "gTZoKB5XwQjnqIOI"
        var isAppForeground: Int = 0
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityStopped(activity: Activity) {
//                Log.i("Simon", "onActivityStopped")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//                Log.i("Simon", "onActivityCreated")
            }

            override fun onActivityResumed(activity: Activity) {

            }

        })
    }

    override fun onTerminate() {
        super.onTerminate()

    }

}