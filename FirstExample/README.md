Ну тут первый пример
1) Кнопки, Активити, Листенеры, ImageView

2) Интент для открытия новой активности

3) Функцию для красоты можно повесить в атрибут кнопки, чтобы не вызывать вручную сеттер

4) Механизм передачи данных через активити через интент, посредтством статической переменной в поле класса как id в мапе

5) Оffensive использование вибратора, webView для отображание страницы поиска

6) Асинхронный http запрос в отдельном потоке (простой Runnable класс) либой okhttp (сам метод синхронный)
Ожидание в этом потоке 3 сек для имитации блокировки ui треда, которой не происходило (проверить можно через switch)

7) AVD manager можно сымулировать девайс, но медленно работает. Дебаг через USB. Можно директории на девайсе в IDE смотреть
Github IDE integration

8) Посмотрел как выглядит исключение в андроиде - тупа вылетает. Stacktrace потом в дебаге (логах) можно посмотреть.

9) Uses-permission в манифесте для использования вибратора, интернета

10) Выравнивание элементов по туториалу для landscape 
	Группировка
	Выравнивание по высоте текста
	Авторасширение