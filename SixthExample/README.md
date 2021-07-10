0) Navigation view (многопунктное меню слева, кнопка в app_bar)
	Тут много моментов, суть - костылесипед
	Navigation graph etc

1) LRU cache
	Без него кстати падало на 15 картинках, memory на профайлере меньше потребляется

2) Content provider контакты
	Можно свой делать, но пока не нужно

3)	Запрос json (простая структура) over http (okhttp)
	get
	post 
	На реальном тестовом API typicode