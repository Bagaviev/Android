package com.example.currencyexchanger

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyexchanger.data.Repository
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val repo = Repository(NetworkModule(), EntityConverter())
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getList()
            tv.post { tv.text = result.toString() + "\n" + result.rates.toString() }
        }


    }
}