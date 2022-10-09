package com.example.currencyexchanger.domain

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */

data class ExchangeModel(
    val base: String,
    val date: String,
    val success: Boolean,
    val rates: List<NormalRate>
)

data class NormalRate(
    val name: String,
    val value: Double
)
