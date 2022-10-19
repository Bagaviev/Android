package com.example.currencyexchanger.domain.interactor

import com.example.currencyexchanger.domain.Repository
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */

class InteractorImpl(
    private val repository: Repository
): Interactor {

    override suspend fun getRates(base: String?): ExchangeModel = repository.loadRatesFromNet(base)

    override suspend fun getSaved() = repository.loadRatesFromDb()

    override suspend fun deleteItem(item: NormalRate) = repository.deleteRateFromDb(item)

    override suspend fun saveItem(item: NormalRate) {
        return repository.saveRateToDb(item)
    }
}