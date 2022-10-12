package com.example.currencyexchanger.domain.interactor

import com.example.currencyexchanger.models.presentation.ExchangeModel

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

interface Interactor {

    suspend fun loadCurrency(base: String?): ExchangeModel?
}