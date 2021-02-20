package com.yds.smartweather

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sunnyweather.android.logic.model.getSky
import com.yds.smartweather.databinding.ActivityWeatherBinding
import com.yds.smartweather.databinding.ForecastItemBinding
import com.yds.smartweather.model.Weather
import com.yds.smartweather.viewmodel.WeatherViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class WeatherActivity : BaseActivity() {
    private lateinit var binding: ActivityWeatherBinding
    val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(binding.root)

        initDrawer()

        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }

        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }

        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }

            binding.swipeRefresh.isRefreshing = false
        })

        //刷新数据
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }

        GlobalScope.launch {
            repeat(100){
                delay(5000)
                Log.i("Simon","isAppForeground = ${MyApplication.isAppForeground}")
            }
        }
    }

    /**
     * 初始化抽屉
     */
    private fun initDrawer() {
        //打开抽屉
        binding.now.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
//                Log.i("Simon","onDrawerClosed")
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerOpened(drawerView: View) {
//                Log.i("Simon","onDrawerOpened")
            }

        })
    }

    /**
     * 关闭抽屉
     */
    fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    /**
     * 刷新天气数据
     */
    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.now.placeName.text = viewModel.placeName
        val realtime = weather.realtime
        val daily = weather.daily
        //填充now布局数据
        val currentTempText = "${realtime.temperature.toInt()}℃"
        binding.now.currentTemp.text = currentTempText
        binding.now.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数${realtime.airQuality.aqi.chn.toInt()}"
        binding.now.currentAQI.text = currentPM25Text
        binding.now.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        //填充forecast布局数据
        binding.forecast.forecastLayout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val forecastItemBinding: ForecastItemBinding =
                ForecastItemBinding.inflate(layoutInflater)
            val view: View = forecastItemBinding.root

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            forecastItemBinding.dateInfo.text = sdf.format(skycon.date)
            val sky = getSky(skycon.value)
            forecastItemBinding.skyIcon.setImageResource(sky.icon)
            forecastItemBinding.skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()}℃"
            forecastItemBinding.temperatureInfo.text = tempText

            binding.forecast.forecastLayout.addView(view)
        }
        //填充life_index
        val lifeIndex = daily.lifeIndex
        binding.lifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        binding.lifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        binding.lifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        binding.lifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.weatherLayout.visibility = View.VISIBLE
    }


}