0) Options menu
	В начале похоже bottomNavigation - делается res/menu.xml с пунктами
	Ну, в общем все делается через коллбеки Активити, onCreateOptionsMenu() и onOptionsItemSelected()
	По итогу отличия с 1ым - там просто отдельные методы спец классов используются.
	
1) Localisations
	Во первых все строки в strings, без хардкода
	Во вторых создаем android resorse directory (не file) и далее протыкиваем тип values, locale, ru
	Далее в translation editor руками переводим, почему то автоматом он этого не делает.
	Все теперь при смене локали язык переводится автоматически.

2) Tests
	