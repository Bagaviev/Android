package com.example.weatherapp.network;

import com.example.weatherapp.pojo.api_entities.Request;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    @GET("onecall?exclude=minutely,hourly,alerts&units=metric&lang=ru")
    Call<Request> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String app_id);
}
