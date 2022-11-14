package com.example.meteohubapp.domain

import android.location.Location
import com.example.meteohubapp.data.db.CityDao
import com.example.meteohubapp.data.location.LocationModule
import com.example.meteohubapp.domain.api_model.RequestMain
import com.example.meteohubapp.domain.our_model.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
interface IRepository {
    fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?, locale: String): Single<RequestMain?>?

    fun loadCitiesByCoordAsync(lat: Double, lon: Double, dbConnector: CityDao): Single<List<City>>

    fun loadAllCitiesAsync(dbConnector: CityDao): Single<List<City>>

    fun loadLocationAsync(locationModule: LocationModule): Maybe<Location?>
}