package com.example.currencyexchanger.data.network

import com.example.currencyexchanger.config.Constants
import com.example.currencyexchanger.domain.RequestEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExchangeRatesApi {

    @GET("latest?")
    suspend fun getRates(
        @Query("base") base: String?,
        @Header("apikey") key: String
    ): Response<RequestEntity?>
}