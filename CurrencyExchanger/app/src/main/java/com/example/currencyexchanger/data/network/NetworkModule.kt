package com.example.currencyexchanger.data.network

import com.example.currencyexchanger.config.Constants.Companion.API_KEY
import com.example.currencyexchanger.config.Constants.Companion.BASE_URL
import com.example.currencyexchanger.models.data.ResponseEntity
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

    override suspend fun getRates(base: String?, apikey: String): Response<ResponseEntity?> {
        return provideRetrofitService().getRates(base, API_KEY)
    }
}