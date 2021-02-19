package com.yds.smartweather.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author YDS
 * @date 2021/2/19
 * @discribe
 */
object WeatherNetwork {

    private val service = RetrofitHelper.create<RetrofitService>()

    //获取城市天气
    suspend fun searchPlaces(query: String) =
        service.searchPlaces(query).await()

    //获取实时天气
    suspend fun getDailyWeather(lng: String, lat: String) =
        service.getDailyWeather(lng, lat).await()

    //获取未来天气
    suspend fun getRealtimeWeather(lng: String, lat: String) =
        service.getRealtimeWeather(lng, lat).await()


    //挂起携程结局多个异步回调
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

            })
        }
    }

}