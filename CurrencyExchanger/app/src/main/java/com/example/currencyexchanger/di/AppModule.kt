package com.example.currencyexchanger.di

import android.app.Application

/**
 * @author Bulat Bagaviev
 * @created 24.10.2022
 */
class AppModule: Application() {
    /*
    В общем пытался сделать DI Hilt'ом, но погряз в ошибках версий компилирования зависимостей других

    Потом попробовал как умею сделать через Dagger, но там бесконечные ошибки с провайдингом Application

    В итоге думал уже сделать Room не в репозитории, а как раньше делал здесь:
    https://github.com/Bagaviev/AndroidSchool2021.2/blob/master/Project/app/src/main/java/com/example/meteohub/di/ApplicationResLocator.kt

    Но там менее корректная архитектура была как мне показалось. В итоге оставил как есть, не так элегантно, но работает неплохо вроде.
    У нас в проекте сейчас это платорменные команды реализовали и есть простые способы накинуть DI, а тут прямо мидл левел уже какой-то имхо.
     */
}