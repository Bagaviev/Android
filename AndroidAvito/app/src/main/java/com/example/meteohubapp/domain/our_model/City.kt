package com.example.meteohubapp.domain.our_model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
@Entity(tableName = "Cities")
data class City (
    @PrimaryKey
    var id: Int,
    var cityName: String,
    var countryName: String,
    var cityNameRu: String,
    var countryNameRu: String,
    var lat: Double,
    var lon: Double
)