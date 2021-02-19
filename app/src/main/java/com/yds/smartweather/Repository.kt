package com.yds.smartweather

import android.util.Log
import androidx.lifecycle.liveData
import com.yds.smartweather.model.PlaceResponse
import com.yds.smartweather.model.Weather
import com.yds.smartweather.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

/**
 * @author YDS
 * @date 2021/2/18
 * @discribe
 */
object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val response = WeatherNetwork.searchPlaces(query)
            if (response.status == "ok") {
                Result.success(response.places)
            } else {
                Result.failure(RuntimeException("runtime status is ${response.status}"))
            }
        } catch (e: Exception) {
            Log.i("Simon", "Exception = ${e.message}")
            Result.failure<List<PlaceResponse.Place>>(e)
        }

        emit(result)
    }

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtime = async {
                    WeatherNetwork.getRealtimeWeather(lng, lat)
                }

                val deferredDaily = async {
                    WeatherNetwork.getDailyWeather(lng, lat)
                }

                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)

                    Result.success(weather)
                }else{
                    Result.failure(RuntimeException("weather status is not ok"))
                }
            }
        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }

        emit(result)
    }


}