package com.example.currencyexchanger.domain

import com.example.currencyexchanger.models.presentation.ExchangeModel

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

interface Repository {

    suspend fun loadModelFromNet(base: String? = null): ExchangeModel
}