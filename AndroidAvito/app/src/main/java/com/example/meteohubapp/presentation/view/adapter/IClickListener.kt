package com.example.meteohubapp.presentation.view.adapter

import com.example.meteohubapp.domain.our_model.WeeklyWeather

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
interface IClickListener {
    fun openItem(position: Int, weather: WeeklyWeather)
}