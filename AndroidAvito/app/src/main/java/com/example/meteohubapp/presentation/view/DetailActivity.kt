package com.example.meteohubapp.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meteohubapp.databinding.ActivityDetailBinding
import com.example.meteohubapp.domain.our_model.WeeklyWeather
import com.example.meteohubapp.utils.Constants
import com.squareup.picasso.Picasso

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dayWeather = intent.getSerializableExtra(ListActivity.BUNDLE_SELECTED_DAY_KEY) as WeeklyWeather
        bindData(dayWeather)
    }

    private fun bindData(dayWeather: WeeklyWeather) {
        with(binding) {
            textViewDtDet.text = dayWeather.dt
            textViewDTempDet.text = dayWeather.dayTemp
            textViewNTempDet.text = dayWeather.nightTemp
            textViewHumDet.text = dayWeather.humidity
            textViewPressDet.text = dayWeather.pressure
            textViewWindSDet.text = dayWeather.windSpeed
            textViewWDegreeDet.text = dayWeather.windDeg
            textViewSriseDet.text = dayWeather.sunrise
            textViewSsetDet.text = dayWeather.sunset
            textViewDpointDet.text = dayWeather.dewPoint
        }

        initIcons(dayWeather)
    }

    private fun initIcons(dayWeather: WeeklyWeather) {
        Picasso.get()
            .load(Constants.BASE_ICON + dayWeather.icon + Constants.ICON_END)
            .fit()
            .into(binding.imageViewDetail)
    }
}