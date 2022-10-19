package com.example.currencyexchanger.domain

import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

interface Repository {

    suspend fun loadRatesFromNet(base: String?): ExchangeModel

    suspend fun loadRatesFromDb(): List<NormalRate>

    suspend fun saveRateToDb(item: NormalRate)

    suspend fun deleteRateFromDb(item: NormalRate)
}