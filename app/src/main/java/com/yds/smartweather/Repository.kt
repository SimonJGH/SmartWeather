package com.yds.smartweather

import androidx.lifecycle.liveData
import com.yds.smartweather.dao.PlaceDao
import com.yds.smartweather.model.PlaceResponse
import com.yds.smartweather.model.Weather
import com.yds.smartweather.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * @author YDS
 * @date 2021/2/18
 * @discribe
 */
object Repository {

    fun savePlace(place: PlaceResponse.Place) = PlaceDao.savePlace(place)

    fun queryPlace() = PlaceDao.queryPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    /**
     * 搜索地方天气
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val response = WeatherNetwork.searchPlaces(query)
        if (response.status == "ok") {
            Result.success(response.places)
        } else {
            Result.failure(RuntimeException("runtime status is ${response.status}"))
        }
    }

    /**
     * 刷新天气信息
     */
    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
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
            } else {
                Result.failure(RuntimeException("weather status is not ok"))
            }
        }
    }

    /**
     * 处理try catch 高阶函数
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }

            emit(result)
        }

}