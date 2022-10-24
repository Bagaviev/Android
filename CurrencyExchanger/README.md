# Репозиторий для задания Беттинг 

## 1) API
* https://apilayer.com/marketplace/exchangerates_data-api<br/>

Заковыристая структура json, решилось хардкодом полей.<br/>

{
  "success": true,
  "symbols": {
    "AED": "United Arab Emirates Dirham",
    "AFN": "Afghan Afghani",
    "ALL": "Albanian Lek",
    "AMD": "Armenian Dram",
    [...]
    }
}<br/>

250 запросов в месяц бесплатно, вторая итерация.<br/>
	
## 2) Что получилось
* - работа с сетью<br/>
* - одна активити, два фрагмента, первая хостит все это, архитектура продиктована bottom navigation<br/>
* - попытка реализовать clean architecture - все слои есть: apiMapper, DbMapper, Repository, Interactor, Viewmodel, Presentation<br/>
* - применить полезный класс вне sdk - SingleLiveEvent для исключения дублирования нежелательный событий<br/>
* - максимально корректно обработать негативные сценарии ошибок в доступном для пользователя виде<br/>

## 3) Что не получилось
* - сделать DI<br/>

## 4) Сложности
* - Структура json > его парсинг<br/>
* - Момент с лишним вызовом api изза особенностей работы выпадающего списка для названий валют<br/>
* - Лишнее срабатывание диалогов об ошибке<br/>

## Использованный стэк:
* Kotlin<br/>
* Room<br/>
* Retrofit<br/>
* Material components<br/>
* SingleLiveEvent<br/>
	
### Примеры скриншотов:

<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/CurrencyExchanger/screen1.jpeg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/CurrencyExchanger/screen2.jpeg" align="left" height="500" width="250" ></a>