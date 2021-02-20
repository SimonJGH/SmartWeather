package com.yds.smartweather.util

import android.content.Context
import android.content.SharedPreferences
import com.yds.smartweather.MyApplication

/**
 * @author YDS
 * @date 2021/2/20
 * @discribe
 */
object SharePreferencesUtil {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences =
            MyApplication.context.getSharedPreferences("normal_msg", Context.MODE_PRIVATE)
    }

    fun containKey(key: String): Boolean? {
        return sharedPreferences?.contains(key)
    }

    fun putString(key: String, value: String) {
        if (value.isNotEmpty()) {
            val edit = sharedPreferences?.edit()
            edit?.putString(key, value)
            edit?.apply()
        }
    }

    fun getString(key: String): String? {
        if (key.isNotEmpty()) {
            return sharedPreferences?.getString(key, "")
        }
        return ""
    }

    fun getStringDefaultValue(key: String, defValue: String): String? {
        if (key.isNotEmpty()) {
            return sharedPreferences?.getString(key, defValue)
        }
        return ""
    }
}