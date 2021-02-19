package com.yds.smartweather.model

import com.google.gson.annotations.SerializedName

/**
 * @author YDS
 * @date 2021/2/18
 * @discribe
 */
data class PlaceResponse(val status: String, val places: List<Place>) {

    data class Place(
        val name: String,
        val location: Location,
        @SerializedName("formatted_address") val address: String
    )

    data class Location(val lng: String, val lat: String)
}
