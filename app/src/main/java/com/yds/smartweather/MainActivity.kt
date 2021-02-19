package com.yds.smartweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.yds.smartweather.databinding.ActivityMainBinding
import com.yds.smartweather.network.RetrofitHelper
import com.yds.smartweather.network.RetrofitService
import kotlinx.coroutines.*
import retrofit2.*
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        /*binding.btRequestBanner.setOnClickListener(View.OnClickListener {
            GlobalScope.launch {
                getBanner()
            }
        })*/

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

    fun main() {
        //实际开发中调用
        val job = Job()
        val coroutineScope = CoroutineScope(job)
        coroutineScope.launch {

        }
        job.cancel()

        //
        GlobalScope.launch {
//            Log.i("Simon", "a")
//            delay(1500)
//            Log.i("Simon", "b")
        }
        Thread.sleep(1000)

        runBlocking {
        }

        val start = System.currentTimeMillis()
        //依次执行
        runBlocking {
            repeat(100000) {
                launch {
                    Log.i("Simon", "$it")
                }
            }
        }

        val end = System.currentTimeMillis()
        val l = end - start
        Log.i("Simon", "stop = $l")
    }

    suspend fun second() = coroutineScope {
        delay(1000)
        launch {

        }
    }

    fun test() {
        runBlocking {
            val async1 = async {
                5 + 6
            }
            val async2 = async {
                5 + 6
            }

            val i = async1.await() + async2.await()
            Log.i("Simon", "result = $i")
        }
    }
}