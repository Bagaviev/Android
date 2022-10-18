package com.example.currencyexchanger.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 18.10.2022
 */
@Database(entities = [NormalRate::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavouriteCurrencies
}