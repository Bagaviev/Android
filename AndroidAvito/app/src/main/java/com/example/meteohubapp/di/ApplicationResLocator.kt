package com.example.meteohubapp.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.room.Room
import com.example.meteohubapp.data.db.AppDatabase
import com.example.meteohubapp.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
open class ApplicationResLocator: Application() {
    private var dbInstance: AppDatabase? = null
    private var locationInstance: LocationManager? = null

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "city.db")
                .createFromAsset("city.db")
                .build()
        }

        if (locationInstance == null)
            locationInstance = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        super.onCreate()
    }

    fun getRoomInstance(): AppDatabase {
        return dbInstance!!
    }

    fun getLocationService(): LocationManager {
        return locationInstance!!
    }

    fun saveToPrefs(city: City) {
        getSharedPreferences("Prefs", MODE_PRIVATE).edit().apply {
            putString("cityName", city.cityName).apply()
            putString("cityNameRu", city.cityNameRu).apply()
            putFloat("lat", city.lat.toFloat()).apply()
            putFloat("lon", city.lon.toFloat()).apply()
        }
    }

    fun readFromPrefs(): City {
        val cityName = getSharedPreferences("Prefs", MODE_PRIVATE).getString("cityName", "-")
        val cityNameRu = getSharedPreferences("Prefs", MODE_PRIVATE).getString("cityNameRu", "-")
        val lat = getSharedPreferences("Prefs", MODE_PRIVATE).getFloat("lat", 0f)
        val lon = getSharedPreferences("Prefs", MODE_PRIVATE).getFloat("lon", 0f)
        return City(-1, cityName!!, "-", cityNameRu!!, "-", lat.toDouble(), lon.toDouble())
    }

    fun getSelf(): ApplicationResLocator {
        return this
    }
}