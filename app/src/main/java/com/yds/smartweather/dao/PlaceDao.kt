package com.yds.smartweather.dao

import android.content.SharedPreferences
import com.google.gson.Gson
import com.yds.smartweather.model.PlaceResponse
import com.yds.smartweather.util.SharePreferencesUtil

/**
 * @author YDS
 * @date 2021/2/20
 * @discribe
 */
object PlaceDao {
    fun savePlace(place: PlaceResponse.Place) {
        SharePreferencesUtil.putString("place", Gson().toJson(place))
    }

    fun queryPlace():PlaceResponse.Place{
        val place = SharePreferencesUtil.getString("place")
        return Gson().fromJson(place,PlaceResponse.Place::class.java)
    }

    fun isPlaceSaved()=SharePreferencesUtil.containKey("place")

}