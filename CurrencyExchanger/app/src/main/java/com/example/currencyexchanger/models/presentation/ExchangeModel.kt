package com.example.currencyexchanger.models.presentation

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */

data class ExchangeModel(
    val base: String,
    val timeLoaded: String,
    val success: Boolean,
    val rates: List<NormalRate>
)

data class NormalRate(
    val name: String,
    val value: Double
)
