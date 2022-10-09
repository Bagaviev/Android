package com.example.currencyexchanger.data

import com.example.currencyexchanger.data.network.NetworkModule

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */

class Repository(
    private val apiMapper: NetworkModule
) {

    suspend fun getList(base: String? = null) =
        apiMapper.getRates(base, "")
}