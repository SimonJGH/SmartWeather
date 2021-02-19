package com.yds.smartweather.model

/**
 * @author YDS
 * @date 2021/2/19
 * @discribe
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily) {
}