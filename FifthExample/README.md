
1) Работа с файлами
	Ну тут пара методов для работы типа openFileOutput() / input
	getFilesDir где сохраняются файлы
	можно через bfrd и file работать

2) Диалог
	AlertDialog.Builder
	create() show()
	
3)	Permission
	Тут моменты были тонкие - проверки checkPermission на факт наличия прав (стандартный метод)
	Потом запросить права если их нет requestPermissions()
	и обработка результата ввода юзера на запрос прав - onRequestPermissionsResult()

4) GPS location
	Есть модный play api для работы с gps
	Есть LocationManager
		Тут requestLocationUpdates() и getLastKnownLocation()
		Надо, чтобы gps был включен (можно самому включить)
	Можно проверить что gps выкл, и редиректить на страницу настроек системных сделать

5) SQLite DB