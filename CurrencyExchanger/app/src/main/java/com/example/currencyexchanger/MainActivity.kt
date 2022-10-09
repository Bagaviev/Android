package com.example.currencyexchanger

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyexchanger.data.Repository
import com.example.currencyexchanger.data.network.NetworkModule
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    private val repo = Repository(NetworkModule())
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        doCall()
    }

    private fun initViews() {
        tv = findViewById(R.id.tv)
    }

    private fun doCall() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = repo.getList()
//            tv.post { tv.text = result.toString() }
//        }

        val client = OkHttpClient().newBuilder().build()

        val request = Request.Builder()
            .url("https://api.apilayer.com/exchangerates_data/latest?")
            .addHeader("apikey", "2Ll1fcI3YQdzP5zxv6KqoxEruS6hPAqC")
            .get()
            .build()

        Thread {
            val response = client.newCall(request).execute();
            Log.e("main", response.body!!.string())
        }.start()
    }
}