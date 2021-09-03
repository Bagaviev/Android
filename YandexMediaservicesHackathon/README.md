# Репозиторий команды 3 + 2

Рабочая директория проекта (приложение): YandexMediaservicesHackathon\HackathonTask\ANDROID_APP<br/>
Финальная ветка: \twitch_streaming<br/>
Apk билд: HackathonTask\ANDROID_APP\app\build\outputs\apk\debug\afisha_3+2.apk<br/>

Привет! Тут наши наработки по проекту Афиша (Android). Кодовая база, + само приложение, + вспомогательные материалы.<br/>

## Основной vision:

Не секрет, что существует приложение Афиши на IOS, но к нашему удивлению, нечто похожее на Android'е мы не нашли.
Поэтому решили реализовать нашу версию, с дополнительной киллер feature: возможностью стриминга оффлайн мероприятий (типа спектаклей) с телефона для организаторов (будь то,
театр, выставка и т.д.) и получения данного стрима пользователем также через приложение.
Дополнительная функциональность которую не успели реализовать, но считаем святым долгом упомянуть - лирика к играющему треку "a la Shazam" для Музыки.
Есть идеи как это сделать, если интересно пишите в телеграм или на почту.
	
### Стек изначальный:
* backend:<br/>
  * Python REST API;<br/>
  * Docker;<br/>
* frontend:<br/>
  * Java Android SDK (приложение);<br/>
  * Exoplayer;<br/>
	
### Стек финальный:
* backend:<br/>
  * Twitch<br/>
* frontend:<br/>
  * Java Android SDK (приложение);<br/>

## Описание:

Мы встроили функционал трансляций оффлайн мероприятий типа спектаклей, пьес в приложение Яндекс Афиши на андроид, и показали наше видение возможного дизайна.
Весь функционал фронтенда реализован в приложении, в качестве бекенда планировалась собственная реализация, в качестве клиента exoplayer, но в итоге остановились на стриминге от twitch. Трансляция с нашего приложения в сеть, и из сети происходят через него.
Кратко про приложение: 3 активити, 4 фрагмента. Все компоненты стандартные, без доп зависимостей. Webview и deeplink для работы с twitch. Списки recyclerview, хранение данных в памяти. Создание объектов руками из ресурсов.

## Ссылки:

Figma: https://clck.ru/X4P9A<br/>
Демо: https://clck.ru/X4P9k<br/>
	
### Примеры скриншотов:

<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic1.jpg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic2.jpg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic3.jpg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic4.jpg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic5.jpg" align="left" height="500" width="250" ></a>
<a href="url"><img src="https://github.com/Bagaviev/Android/blob/master/YandexMediaservicesHackathon/pic6.jpg" align="left" height="500" width="250" ></a>
