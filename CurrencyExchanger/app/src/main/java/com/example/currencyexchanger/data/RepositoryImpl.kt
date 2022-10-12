package com.example.currencyexchanger.data

import com.example.currencyexchanger.config.Constants.Companion.EMPTY_STRING
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.domain.Repository
import com.example.currencyexchanger.models.presentation.ExchangeModel

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */
class RepositoryImpl(
    private val apiMapper: NetworkModule,
    private val converter: EntityConverter
): Repository {

    override suspend fun loadModelFromNet(base: String?): ExchangeModel {
        val result = apiMapper.getRates(base, EMPTY_STRING)
        return converter.convert(result.body())
    }
}