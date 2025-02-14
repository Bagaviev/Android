package com.example.meteohubapp.domain.api_model

import com.google.gson.annotations.SerializedName

// https://www.json2kotlin.com/

data class Current(
    @SerializedName("dt")
    var dt: Int,

    @SerializedName("sunrise")
    var sunrise: Int,

    @SerializedName("sunset")
    var sunset: Int,

    @SerializedName("temp")
    var temp: Double,

    @SerializedName("feels_like")
    var feelsLike: Double,

    @SerializedName("pressure")
    var pressure: Int,

    @SerializedName("humidity")
    var humidity: Int,

    @SerializedName("dew_point")
    var dewPoint: Double,

    @SerializedName("uvi")
    var uvi: Double,

    @SerializedName("clouds")
    var clouds: Int,

    @SerializedName("visibility")
    var visibility: Int,

    @SerializedName("wind_speed")
    var windSpeed: Double,

    @SerializedName("wind_deg")
    var windDeg: Int,

    @SerializedName("weather")
    var weather: List<Weather>?)