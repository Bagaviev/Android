package com.example.meteohubapp.data.converter

import com.example.meteohubapp.domain.api_model.RequestMain
import com.example.meteohubapp.domain.our_model.WeeklyWeather
import com.example.meteohubapp.utils.Constants.Companion.EN_LOCALE
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class Converter {

    companion object {
        /**  */
        private const val HPA_TO_HGMM_COEF = 1.333

        /**
         * Конвертер и форматтер типов данных json, конвертирует в английский язык
         * Метод преобразующий объект запроса из сети RequestMain к самодельному списку объектов WeeklyWeather.
         *
         * @param mainRequest объект запроса из OkHttpClient, составной json, содержит полный набор атрибутов предметной области погоды.
         * @return Список вспомогательных объектов, с сокращенным под наши нужды набором атрибутов, приведенными типами, а также с заданным форматированием для вывода..
         */

        fun convert(mainRequest: RequestMain): List<WeeklyWeather> {
            val result = arrayListOf<WeeklyWeather>()
            val dailies = mainRequest.daily

            if (dailies != null) {

                val mainDateFormat = SimpleDateFormat("EEEE, d MMMM", Locale(EN_LOCALE))
                val additionalDateFormat = SimpleDateFormat("HH:mm", Locale(EN_LOCALE))

                for (day in dailies) {
                    val dt = mainDateFormat.format(Date(day.dt * 1000))
                    val dayTemp = day.temp?.day
                    val nightTemp = day.temp?.night
                    val pressure = day.pressure / HPA_TO_HGMM_COEF
                    val humidity = day.humidity
                    val windSpeed = day.windSpeed
                    val description = day.weather?.get(0)?.description?.lowercase()
                    val sunrise = additionalDateFormat.format(Date(day.sunrise.toLong() * 1000))
                    val sunset = additionalDateFormat.format(Date(day.sunset.toLong() * 1000))
                    val windDegree = day.windDeg
                    val dewPoint = day.dewPoint
                    val sunriseRaw = additionalDateFormat.parse(additionalDateFormat.format(Date(day.sunrise.toLong() * 1000)))
                    val sunsetRaw = additionalDateFormat.parse(additionalDateFormat.format(Date(day.sunset.toLong() * 1000)))
                    val icon = day.weather?.get(0)?.icon

                    result.add(
                        WeeklyWeather(
                            dt.capitalize(),
                            "Day: " + String.format("%d", Math.round(dayTemp!!)) + "°C",
                            "Night: " + String.format("%d", Math.round(nightTemp!!)) + "°C",
                            "Pressure: " + String.format("%.0f", pressure) + " mm.Hg",
                            "Humidity: " + String.format("%d", humidity) + "%",
                            "Wind: " + String.format("%.0f", windSpeed) + " m/s",
                            description?.capitalize(),
                            "Dawn: $sunrise",
                            "Sunset: $sunset",
                            "Wind direction: " + String.format("%d", windDegree) + "°",
                            "Dew point: " + String.format("%d", Math.round(dewPoint!!)) + "°C",
                            sunriseRaw,
                            sunsetRaw,
                            icon
                        )
                    )
                }
            }
            return result
        }
    }
}