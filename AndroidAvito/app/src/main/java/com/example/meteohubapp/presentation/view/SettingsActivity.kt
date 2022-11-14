package com.example.meteohubapp.presentation.view

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meteohubapp.databinding.ActivitySettingsBinding
import com.example.meteohubapp.di.ApplicationResLocator
import com.example.meteohubapp.domain.IRepository
import com.example.meteohubapp.domain.our_model.City
import com.example.meteohubapp.presentation.viewmodel.SettingsActivityViewModel
import com.example.meteohubapp.utils.Constants.Companion.GPS_PERMISSION_CODE
import com.example.meteohubapp.utils.Constants.Companion.RU_LOCALE
import com.example.meteohubapp.utils.Utility
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsActivityViewModel: SettingsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createViewModel()
        subscribeForLiveData()

        settingsActivityViewModel.loadAllCities()

        binding.imageButtonGps.setOnClickListener { handleGps() }
        binding.searchView.setOnQueryTextFocusChangeListener { _, _ ->
            binding.searchResultsList.visibility = View.VISIBLE
        }
    }

    private fun initSearchView(cities: List<City>) {
        val keys: List<String> = if (applicationContext.resources.configuration.locale.language.equals(RU_LOCALE))
            cities.map { it.cityNameRu + ", " + it.countryNameRu }
        else
            cities.map { it.cityName + ", " + it.countryName }

        val cityListMappingCache = keys.zip(cities).toMap() as HashMap<String, City>
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, keys)

        with(binding) {
            searchResultsList.adapter = adapter

            searchResultsList.setOnItemClickListener { parent, _, position, _ ->
                val cityNameSelected = parent.getItemAtPosition(position) as String

                settingsActivityViewModel.applicationResLocator.saveToPrefs(cityListMappingCache[cityNameSelected]!!)
                showSelectedCity(cityListMappingCache[cityNameSelected]!!)

                searchView.clearFocus()
                searchResultsList.visibility = View.INVISIBLE
            }

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String) = false

                override fun onQueryTextChange(newText: String): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }
    }

    private fun showSelectedCity(city: City) {
        val cityNameLocalised = if (applicationContext.resources.configuration.locale.language.equals(RU_LOCALE)) city.cityNameRu
        else city.cityName

        Toast.makeText(
            this@SettingsActivity,
            getString(com.example.meteohubapp.R.string.city_selected) + " " + cityNameLocalised,
            Toast.LENGTH_LONG
        ).show()

        binding.selectedCityTv.apply {
            text = getString(com.example.meteohubapp.R.string.city_selected_short) + " " + cityNameLocalised
            visibility = View.VISIBLE
        }
    }

    private fun createViewModel() {
        val repository: IRepository =
            (applicationContext as ApplicationResLocator).appComponent.getRepository()

        settingsActivityViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsActivityViewModel(
                    repository,
                    (applicationContext as ApplicationResLocator).getSelf()
                ) as T
            }
        }).get(SettingsActivityViewModel::class.java)
    }

    private fun subscribeForLiveData() {
        settingsActivityViewModel.getAllCitiesLiveData().observe(this, this::initSearchView)
        settingsActivityViewModel.getCityByCoordsLiveData().observe(this, this::showSelectedCity)
        settingsActivityViewModel.getProgressLiveData().observe(this, this::showProgress)
        settingsActivityViewModel.getErrorLiveData().observe(this, this::showError)
    }

    private fun showError(error: Throwable) {
        if (error is NoSuchElementException)
            handleAtlanticOceanLocation()
        else
            Snackbar.make(binding.root, error.toString(), BaseTransientBottomBar.LENGTH_LONG)
                .show();
    }

    private fun showProgress(isVisible: Boolean) {
        with(binding) {
            if (isVisible) progressBar2.visibility = View.VISIBLE
            else progressBar2.visibility = View.INVISIBLE
        }
    }

    private fun handleNoGpsModule() {
        val dialog = Utility.provideAlertDialog(
            this,
            resources.getString(com.example.meteohubapp.R.string.no_gps_module)
        )
        dialog.show()
    }

    private fun handleDenyPermission() {
        val dialog = Utility.provideAlertDialog(
            this,
            resources.getString(com.example.meteohubapp.R.string.no_permission)
        )
        dialog.show()
    }

    private fun handleDenyPermissionRoughly() {
        val dialog = Utility.provideAlertDialog(
            this,
            resources.getString(com.example.meteohubapp.R.string.no_permission_roughly)
        )
        dialog.show()
    }

    private fun handleAtlanticOceanLocation() {
        val dialog = Utility.provideAlertDialog(
            this,
            resources.getString(com.example.meteohubapp.R.string.city_not_found)
        )
        dialog.show()
    }

    private fun handleGps() {
        if (settingsActivityViewModel.locationModule!!.isGpsAvailableOnDevice()) {

            if (settingsActivityViewModel.locationModule!!.isLocationGranted()) {
                settingsActivityViewModel.locationModule!!.handleGpsSettings(this)
                settingsActivityViewModel.getCurrentCity()
            } else
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    GPS_PERMISSION_CODE
                )
        } else
            handleNoGpsModule()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            GPS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        settingsActivityViewModel.locationModule!!.handleGpsSettings(this)
                        settingsActivityViewModel.getCurrentCity()
                    } catch (e: SecurityException) {
                        showError(e)
                    }
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                    handleDenyPermission()
                else
                    handleDenyPermissionRoughly()
            }
        }
    }
}