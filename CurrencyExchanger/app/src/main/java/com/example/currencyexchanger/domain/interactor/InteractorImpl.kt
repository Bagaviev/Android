package com.example.currencyexchanger.domain.interactor

import com.example.currencyexchanger.domain.Repository
import com.example.currencyexchanger.models.presentation.ExchangeModel

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

class InteractorImpl(
    private val repository: Repository
): Interactor {

    override suspend fun getRatesDefault(): ExchangeModel = repository.loadRatesDefaultCurrency()

    override suspend fun getRatesSpecific(base: String): ExchangeModel = repository.loadRatesSpecificCurrency(base)
}