package com.example.currencyexchanger.data

import android.app.Application
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.database.AppDatabase
import com.example.currencyexchanger.data.database.FavouriteCurrencies
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.domain.Repository
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */
class RepositoryImpl(
    private val apiMapper: NetworkModule,
    private val converter: EntityConverter,
    application: Application
): Repository {
    private val fDao: FavouriteCurrencies = AppDatabase.getInstance(application).favoritesDao()

    override suspend fun loadRatesFromNet(base: String?): ExchangeModel {
        val result = apiMapper.apiCallLatest(base)
        return converter.convert(result.body())
    }

    override suspend fun loadRatesFromDb() = fDao.getAll()

    override suspend fun deleteRateFromDb(item: NormalRate) = fDao.deleteOne(item)

    override suspend fun saveRateToDb(item: NormalRate) {
        return fDao.insertOne(item)
    }
}