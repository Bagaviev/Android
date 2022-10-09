package com.example.currencyexchanger.domain

import com.google.gson.annotations.SerializedName

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */
data class RequestEntity(
    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val rates: List<Rates>,

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("timestamp")
    val timestamp: Int
)