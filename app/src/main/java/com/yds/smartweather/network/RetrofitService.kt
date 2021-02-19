package com.yds.smartweather.network

import com.yds.smartweather.MyApplication
import com.yds.smartweather.model.DailyResponse
import com.yds.smartweather.model.PlaceResponse
import com.yds.smartweather.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * @author YDS
 * @date 2021/2/9
 * @discribe
 */
interface RetrofitService {

    //获取城市天气
    @GET("v2/place?token=${MyApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String):Call<PlaceResponse>

    //获取实时天气
    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    //获取未来天气
    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>


}