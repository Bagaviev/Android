package com.example.meteohubapp.data.network

import com.example.meteohubapp.domain.api_model.RequestMain
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface IOpenWeatherApi {

    // динамически передавать локаль в GET через path не удалось, дублируем
    @GET("onecall?exclude=minutely,hourly,alerts&units=metric&lang=en")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") app_id: String?
    ): Single<RequestMain?>?

    @GET("onecall?exclude=minutely,hourly,alerts&units=metric&lang=ru")
    fun getWeatherRu(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") app_id: String?
    ): Single<RequestMain?>?
}