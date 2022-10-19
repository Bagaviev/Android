package com.example.currencyexchanger.data.network

import android.util.Log
import com.example.currencyexchanger.models.data.ResponseEntity
import com.example.currencyexchanger.utils.Constants.Companion.API_KEY_NEW
import com.example.currencyexchanger.utils.Constants.Companion.API_KEY_OLD
import com.example.currencyexchanger.utils.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Bulat Bagaviev
 */
class NetworkModule {

    private fun provideRetrofitService(): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    suspend fun apiCallLatest(base: String?): Response<ResponseEntity?> {
        Log.e("network", "apicall")
        return provideRetrofitService().apiCallLatest(base, API_KEY_NEW)
    }
}