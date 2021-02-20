package com.yds.smartweather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.yds.smartweather.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.*
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

    }

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

    fun coroutineScopes() {
        //实际开发中调用
        val job = Job()
        val coroutineScope = CoroutineScope(job)
        coroutineScope.launch {

        }
        job.cancel()

        GlobalScope.launch {
        }

        //依次执行
        runBlocking {
            repeat(100000) {
                launch {
                    Log.i("Simon", "$it")
                }
            }
        }

    }

    suspend fun second() = coroutineScope {
        delay(1000)
        launch {

        }
    }

}