package com.example.currencyexchanger.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.currencyexchanger.models.presentation.NormalRate

/**
 * @author Bulat Bagaviev
 * @created 18.10.2022
 */
@Database(entities = [NormalRate::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavouriteCurrencies

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context).also { INSTANCE = it } }

        private fun buildDatabase(context: Context) =
            databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "favourite_rates.db")
                .build()
    }
}