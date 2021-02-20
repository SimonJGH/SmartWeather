package com.yds.smartweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yds.smartweather.Repository
import com.yds.smartweather.model.PlaceResponse

/**
 * @author YDS
 * @date 2021/2/20
 * @discribe
 */

class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<PlaceResponse.Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData) {
        Repository.refreshWeather(it.lng, it.lat)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = PlaceResponse.Location(lng, lat)
    }

}