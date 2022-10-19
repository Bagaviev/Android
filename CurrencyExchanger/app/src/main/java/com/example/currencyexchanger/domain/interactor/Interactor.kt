package com.example.currencyexchanger.domain.interactor

import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

interface Interactor {

    suspend fun getRates(base: String?): ExchangeModel

    suspend fun getSaved(): List<NormalRate>

    suspend fun saveItem(item: NormalRate)

    suspend fun deleteItem(item: NormalRate)
}