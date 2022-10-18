package com.example.currencyexchanger.data

import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.domain.Repository
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.utils.Constants.Companion.EMPTY_STRING

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */
class RepositoryImpl(
    private val apiMapper: NetworkModule,
    private val converter: EntityConverter
): Repository {

    override suspend fun loadRates(base: String?): ExchangeModel {
        val result = apiMapper.apiCallLatest(base, EMPTY_STRING)
        return converter.convert(result.body())
    }
}