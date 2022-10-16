package com.example.currencyexchanger.data.network

import com.example.currencyexchanger.models.data.ResponseEntity
import com.example.currencyexchanger.utils.Constants.Companion.API_KEY
import com.example.currencyexchanger.utils.Constants.Companion.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Bulat Bagaviev
 */
class NetworkModule: ExchangeRatesApi {

    private fun provideRetrofitService(): ExchangeRatesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRatesApi::class.java)
    }

    override suspend fun apiCallLatest(base: String?, apiKey: String): Response<ResponseEntity?> {
        return provideRetrofitService().apiCallLatest(base, API_KEY)
    }
}