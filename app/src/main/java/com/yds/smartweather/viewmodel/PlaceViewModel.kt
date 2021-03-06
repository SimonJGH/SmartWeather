package com.yds.smartweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yds.smartweather.Repository
import com.yds.smartweather.model.PlaceResponse

/**
 * @author YDS
 * @date 2021/2/18
 * @discribe
 */
class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<PlaceResponse.Place>()

    fun savePlace(place: PlaceResponse.Place) = Repository.savePlace(place)

    fun queryPlace() = Repository.queryPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    //观察
    val palceLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

}