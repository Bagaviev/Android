package com.example.meteohubapp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.startActivity
import com.example.meteohubapp.di.ApplicationResLocator
import com.example.meteohubapp.domain.our_model.City
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class LocationModule(var applicationResLocator: ApplicationResLocator) {

    @SuppressLint("MissingPermission")
    fun findLocation(): Location? {
        val locationManager = applicationResLocator.getLocationService()
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, {}, null)

        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }

    fun isLocationGranted() =
        checkSelfPermission(applicationResLocator, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    fun handleGpsSettings(context: Context) {
        if (!applicationResLocator.getLocationService().isProviderEnabled(LocationManager.GPS_PROVIDER))
            startActivity(context, Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), null)
    }

    fun isGpsAvailableOnDevice() =
        applicationResLocator.packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)

    private fun calcDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val locA = Location("point A")
        val locB = Location("point B")

        locA.latitude = lat1
        locA.longitude = lon1

        locB.latitude = lat2
        locB.longitude = lon2

        return locA.distanceTo(locB)
    }

    fun getClosestCity(locationToCalc: Location, cities: List<City>): City {
        val map: MutableMap<Float, City> = TreeMap()

        for (i in cities.indices) {
            val distance = calcDistance(locationToCalc.latitude, locationToCalc.longitude,
                cities[i].lat, cities[i].lon)

            map[distance] = cities[i]
        }
        val actualValue: Map.Entry<Float, City> = map.entries
            .stream()
            .findFirst()
            .get()
        return actualValue.value
    }
}
