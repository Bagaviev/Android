package com.example.meteohubapp.domain.api_model

import com.google.gson.annotations.SerializedName

data class FeelsLike (
    @SerializedName("day")
    var day: Double,

    @SerializedName("night")
    var night: Double,

    @SerializedName("eve")
    var eve: Double,

    @SerializedName("morn")
    var morn: Double)