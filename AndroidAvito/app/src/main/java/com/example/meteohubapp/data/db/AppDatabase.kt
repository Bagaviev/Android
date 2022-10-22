package com.example.meteohubapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meteohubapp.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
@Database(entities = [City::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}