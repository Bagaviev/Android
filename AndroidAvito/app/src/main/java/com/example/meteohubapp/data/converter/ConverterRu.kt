package com.example.meteohubapp.data.converter

import com.example.meteohubapp.domain.api_model.RequestMain
import com.example.meteohubapp.domain.our_model.WeeklyWeather
import com.example.meteohubapp.utils.Constants.Companion.RU_LOCALE
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class ConverterRu {

    companion object {
        /**  */
        private const val HPA_TO_HGMM_COEF = 1.333

        /**
         * Конвертер и форматтер типов данных json, конвертирует в русский язык
         * Метод преобразующий объект запроса из сети RequestMain к самодельному списку объектов WeeklyWeather.
         *
         * @param mainRequest объект запроса из OkHttpClient, составной json, содержит полный набор атрибутов предметной области погоды.
         * @return Список вспомогательных объектов, с сокращенным под наши нужды набором атрибутов, приведенными типами, а также с заданным форматированием для вывода..
         */

        fun convert(mainRequest: RequestMain): List<WeeklyWeather> {
            val result = arrayListOf<WeeklyWeather>()
            val dailies = mainRequest.daily

            if (dailies != null) {

                val mainDateFormat = SimpleDateFormat("EEEE, d MMMM", Locale(RU_LOCALE))
                val additionalDateFormat = SimpleDateFormat("HH:mm", Locale(RU_LOCALE))

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
                            "День: " + String.format("%d", Math.round(dayTemp!!)) + "°C",
                            "Ночь: " + String.format("%d", Math.round(nightTemp!!)) + "°C",
                            "Давление: " + String.format("%.0f", pressure) + " мм.рт",
                            "Влажность: " + String.format("%d", humidity) + "%",
                            "Ветер: " + String.format("%.0f", windSpeed) + " м/с",
                            description?.capitalize(),
                            "Рассвет: $sunrise",
                            "Закат: $sunset",
                            "Направление ветра: " + String.format("%d", windDegree) + "°",
                            "Точка росы: " + String.format("%d", Math.round(dewPoint!!)) + "°C",
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