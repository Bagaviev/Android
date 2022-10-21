package com.example.meteohubapp.presentation.view.adapter

import com.example.meteohubapp.domain.our_model.WeeklyWeather

/**
 * @author Bulat Bagaviev
 * @created 14.11.2021
 */

interface IClickListener {
    fun openItem(position: Int, weather: WeeklyWeather)
}