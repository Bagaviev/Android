package com.example.currencyexchanger.di

import android.app.Application
import androidx.room.Room
import com.example.currencyexchanger.data.database.AppDatabase

/**
 * @author Bulat Bagaviev
 * @created 18.10.2022
 */
open class AppResLocator: Application() {
    var appDb: AppDatabase? = null

    override fun onCreate() {
        if (appDb == null) {
            appDb = Room
                .databaseBuilder(applicationContext, AppDatabase::class.java, "favourites.db")
                .build()
        }
        super.onCreate()
    }
}