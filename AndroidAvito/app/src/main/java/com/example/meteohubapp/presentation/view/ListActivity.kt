package com.example.meteohubapp.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.meteohubapp.R
import com.example.meteohubapp.databinding.ActivityListBinding
import com.example.meteohubapp.di.ApplicationResLocator
import com.example.meteohubapp.domain.IRepository
import com.example.meteohubapp.domain.our_model.City
import com.example.meteohubapp.domain.our_model.WeeklyWeather
import com.example.meteohubapp.presentation.view.adapter.IClickListener
import com.example.meteohubapp.presentation.view.adapter.WeatherListAdapter
import com.example.meteohubapp.presentation.viewmodel.ListActivityViewModel
import com.example.meteohubapp.utils.Constants
import com.example.meteohubapp.utils.Constants.Companion.RU_LOCALE
import com.example.meteohubapp.utils.Utility
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var listActivityViewModel: ListActivityViewModel
    private lateinit var savedCity: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Utility.isNetworkAvailable(this)) {
            val dialog = Utility.provideAlertDialog(this, resources.getString(R.string.no_network_connection))
            setupDialog(this, Utility, dialog)
        } else {
            initRecycler()
            createViewModel()
            subscribeForLiveData()
            initSwipeRefresh()
            makeRequest()
        }

        if (applicationContext.resources.configuration.locales[0].country.equals(RU_LOCALE.uppercase())) {
            showVpnDisclaimer()
        }
    }

    private fun createViewModel() {
        val repository: IRepository = (applicationContext as ApplicationResLocator).appComponent.getRepository()

        listActivityViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ListActivityViewModel(repository) as T
            }
        }).get(ListActivityViewModel::class.java)
    }

    private fun subscribeForLiveData() {
        listActivityViewModel.getWeatherLiveData().observe(this, this::showData)
        listActivityViewModel.getProgressLiveData().observe(this, this::showProgress)
        listActivityViewModel.getErrorLiveData().observe(this, this::showError)
    }

    private fun makeRequest() {
        handleSavedCity()

        if (applicationContext.resources.configuration.locale.language.equals(RU_LOCALE))
            listActivityViewModel.loadWeatherRu(savedCity.lat, savedCity.lon)
        else
            listActivityViewModel.loadWeather(savedCity.lat, savedCity.lon)

        binding.buttonSettings.setOnClickListener { startSettings() }
    }

    private fun showError(error: Throwable) {
        Snackbar.make(binding.root, error.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun showVpnDisclaimer() {
        Snackbar.make(binding.root, resources.getString(R.string.vpn_msg), Snackbar.LENGTH_LONG).show()
    }

    private fun showProgress(isVisible: Boolean) {
        with(binding) {
            if (isVisible) progressBar.visibility = View.VISIBLE
            else progressBar.visibility = View.GONE
        }
    }

    private fun showData(weatherList: List<WeeklyWeather>) {
        with(binding) {
            setUpTableauData(weatherList[0])
            handleDayNightTableau(weatherList[0])
            initIcons(weatherList[0])

            swipeRefresh.isRefreshing = false
            viewToday.setOnClickListener { startDetail(weatherList[0]) }

            recView.adapter = WeatherListAdapter(weatherList.subList(1, weatherList.size - 1), object: IClickListener {
                override fun openItem(position: Int, weather: WeeklyWeather) { startDetail(weather) }
            })

            recView.adapter?.notifyDataSetChanged()
        }
    }

    private fun initRecycler() {
        with(binding) {
            val itemDecoration = DividerItemDecoration(recView.context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(getDrawable(R.drawable.recycler_vertical_divider)!!)
            recView.addItemDecoration(itemDecoration)
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener { onRefresh() }
    }

    private fun onRefresh() {
        if (applicationContext.resources.configuration.locale.language.equals(RU_LOCALE))
            listActivityViewModel.loadWeatherRu(savedCity.lat, savedCity.lon)
        else
            listActivityViewModel.loadWeather(savedCity.lat, savedCity.lon)

        Toast.makeText(this@ListActivity, getString(R.string.refresh_msg), Toast.LENGTH_LONG).show()
    }

    private fun handleSavedCity() {
        savedCity = (application as ApplicationResLocator).readFromPrefs()

        if (savedCity.lat == 0.0) {
            val dialog = Utility.provideAlertDialog(this, resources.getString(R.string.no_city_selected))
            setupDialog(this, Utility, dialog)
            startSettings()
        }
    }

    private fun startDetail(weather: WeeklyWeather) {
        val intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BUNDLE_SELECTED_DAY_KEY, weather)
        startActivity(intent)
    }

    private fun startSettings() {
        val intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun setUpTableauData(todayData: WeeklyWeather) {
        val cityNameLocalised = if (applicationContext.resources.configuration.locale.language.equals(RU_LOCALE)) savedCity.cityNameRu
        else savedCity.cityName

        binding.apply {
            textViewCity.text = cityNameLocalised
            textViewTodayDayT.text = todayData.dayTemp
            textViewTodayNightT.text = todayData.nightTemp
            textViewTodayWindS.text = todayData.windSpeed
            textViewTodayDesc.text = todayData.description
        }
    }

    private fun handleDayNightTableau(todayData: WeeklyWeather) {
        val additionalDateFormat = SimpleDateFormat("HH:mm", Locale(RU_LOCALE))
        val now = additionalDateFormat.parse(additionalDateFormat.format(Date()))

        if (now > todayData.sunriseRaw && now < todayData.sunsetRaw)
            colorizeViewBackground(false)
        else
            colorizeViewBackground(true)
    }

    private fun colorizeViewBackground(isGeoNight: Boolean) {
        if (!isDarkMode()) {
            binding.viewToday.background?.colorFilter = when (isGeoNight) {
                true -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_night), BlendModeCompat.SRC_ATOP)
                false -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_day), BlendModeCompat.SRC_ATOP)
            }
        } else {
            binding.viewToday.background?.colorFilter = when (isGeoNight) {
                true -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_night_dark), BlendModeCompat.SRC_ATOP)
                false -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_day_dark), BlendModeCompat.SRC_ATOP)
            }
        }
    }

    private fun isDarkMode(): Boolean {
        val nightModeFlags = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
    }

    private fun initIcons(todayData: WeeklyWeather) {
        Picasso.get()
            .load(Constants.BASE_ICON + todayData.icon + Constants.ICON_END)
            .fit()
            .into(binding.imageViewToday)
    }

    private fun setupDialog(context: Context, utils: Utility, dialog: AlertDialog) {
        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)

            button.setOnClickListener {
                if (utils.isNetworkAvailable(context)) {
                    createViewModel()
                    subscribeForLiveData()
                    makeRequest()
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    companion object {
        var BUNDLE_SELECTED_DAY_KEY: String? = "BUNDLE_SELECTED_DAY_KEY"
    }
}
