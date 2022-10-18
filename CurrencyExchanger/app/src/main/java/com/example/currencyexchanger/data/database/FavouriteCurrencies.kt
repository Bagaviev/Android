package com.example.currencyexchanger.data.database

import androidx.room.*
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 18.10.2022
 */
@Dao
interface FavouriteCurrencies {
    @Query("SELECT * FROM SavedRates")
    suspend fun getAll(): List<NormalRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(favouriteRate: NormalRate)

    @Delete
    suspend fun deleteOne(rateToDelete: NormalRate)
}