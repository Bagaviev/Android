package com.example.meteohubapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.meteohubapp.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
@Dao
interface CityDao {
    @Query("SELECT * FROM Cities WHERE abs(lat - :latArg) < 0.5 AND abs(lon - :lonArg) < 0.5")
    fun getCloseCitiesByCoords(latArg: Double, lonArg: Double): List<City>

    @Query("SELECT * FROM Cities")
    fun getAllCities(): List<City>

    @Insert
    fun insertAll(cities: List<City>)
}