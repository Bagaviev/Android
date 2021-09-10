# Репозиторий для задания Авито 

Задание: https://github.com/avito-tech/android-trainee-task-2021<br/>
Финальная ветка: /master<br/>
Apk билд: https://github.com/Bagaviev/Android/blob/master/AvitoTask/app/build/outputs/apk/debug/app-debug.apk<br/>

## 1) Тестируем API на достаточность данных
- Postman (Город, день - неделя, погода)<br/>

* api.openweathermap.org/data/2.5/weather?q=London&appid={API key}<br/>
работает, но без прогноза, только текущее.<br/>
* api.openweathermap.org/data/2.5/forecast/daily?q=moscow&cnt=7&appid=4e58c5be8ff989deef7e876753dfb670<br/>
платно не работает.<br/>
* api.openweathermap.org/data/2.5/onecall?lat=55.727649&lon=37.536683&exclude=minutely,hourly,alerts&units=metric&lang=ru&appid=4e58c5be8ff989deef7e876753dfb670<br/>
тут придется поковыряться с центрами координат городов.<br/>

все остальные - меньше данных (нет нужных), или очень ограниченное кол-во запросов (60день против 60/мин)<br/>
	
## 2) Делаем прототип на Java
* - работа с сетью (запрос к OpenWeatherAPI)<br/>
* - логика фильтров городов (кастомный список из открытых источников, 40к крупнейших городов по всему миру)<br/>
* - выделение текущего города по GPS (кастомный расчет дистанции по great-circle)<br/>
* - выбор любого города из списка как основного<br/>
* - экраны, внешний вид<br/>
* - строка поиска<br/>
* - progressbar<br/>
* - обработка отсутствия интернета<br/>
* - warning для первого запуска (интент выбора города)<br/>
* - микротест на планшете<br/>
* - readme (скрины + описание это)<br/>
* - apk<br/>

## 3) Закидываем в гит, и кидаем ссылку на стажировку

## Backlog (на потом):
* - тредировать, где нужно (сеть сделана, работа с файлами и расчеты дистанций)<br/>
* - хранение данных persist не завелось, но в идеале надо сделать. Пока что все данные крутятся in-memory<br/>
* - архитектуру нужно проработать (разделить методы на классы, накрутить mvc)<br/>
* - покрасивее стили и анимации добавить<br/>
  * - dragdown update layout<br/>
  * - searchView вместо editText<br/>
  * - индикатор мб по температуре<br/>
  * - listView не реагирует на удержание странно<br/>
* - портировать на Kotlin<br/>
* - разумеется adView проработать как и добавить<br/>

## Использованный стэк:
* Java<br/>
* OpenWeatherAPI<br/>
* Retrofit<br/>
* Location Gps<br/>
* MinSDK > Android 7.0 Nougat [24] (With Gps)<br/>
	
### Примеры скриншотов:

<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/AvitoTask/1.jpeg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/AvitoTask/2.jpeg" align="left" height="500" width="250" ></a>