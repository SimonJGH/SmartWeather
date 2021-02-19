package com.yds.smartweather.model

import com.google.gson.annotations.SerializedName

/**
 * @author YDS
 * @date 2021/2/19
 * @discribe 实时天气
 */
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}