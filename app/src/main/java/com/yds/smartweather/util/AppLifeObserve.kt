package com.yds.smartweather.util

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yds.smartweather.MyApplication

/**
 * @author YDS
 * @date 2021/2/7
 * @discribe 声明周期观察
 */
class AppLifeObserve(val lifecycle: Lifecycle) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
//        Log.i("Simon", "activityStart + ${MyApplication.isAppForeground}")
        MyApplication.isAppForeground = MyApplication.isAppForeground + 1
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityResume() {
//        Log.i("Simon", "activityResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun activityPause() {
//        Log.i("Simon", "activityPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
//        Log.i("Simon", "activityStop")
        MyApplication.isAppForeground= MyApplication.isAppForeground-1
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityDestroy() {
//        Log.i("Simon", "activityDestroy")
    }

}