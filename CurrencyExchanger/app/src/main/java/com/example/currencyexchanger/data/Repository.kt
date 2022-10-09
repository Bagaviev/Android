package com.example.currencyexchanger.data

import com.example.currencyexchanger.config.Constants.Companion.EMPTY_STRING
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.domain.ExchangeModel

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */
class Repository(
    private val apiMapper: NetworkModule,
    private val converter: EntityConverter
) {
    suspend fun getList(base: String? = null): ExchangeModel {
        val result = apiMapper.getRates(base, EMPTY_STRING)
        return converter.convert(result.body())
    }
}