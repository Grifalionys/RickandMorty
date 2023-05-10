# Rick and Morty
Приложение написано на Kotlin и Java

## Оглавление 
* [Основная информация](https://github.com/Grifalionys/RickandMorty/blob/develop/README.md#%D0%BE%D1%81%D0%BD%D0%BE%D0%B2%D0%BD%D0%B0%D1%8F-%D0%B8%D0%BD%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%86%D0%B8%D1%8F)

* [Технологии](https://github.com/Grifalionys/RickandMorty/blob/develop/README.md#%D1%82%D0%B5%D1%85%D0%BD%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8%D0%B8)

* [Скриншоты](https://github.com/Grifalionys/RickandMorty/blob/develop/README.md#%D1%81%D0%BA%D1%80%D0%B8%D0%BD%D1%88%D0%BE%D1%82%D1%8B)



## Основная информация
При запуске приложения отображается Splash Screen. Эта активность отображается в качестве фона для системного окна, изображения основных персонажей, символизирующих приложение.

Приложение содержит нижнюю навигацию (BottomNavigationView) с 3 вкладками: персонажи, локации, эпизоды. Каждая вкладка имеет доступ к фильтрации и поиску для этой вкладки. При нажатии на элемент из списка открывается экран с детальной информацией.

Приложение поддерживает кеширование и имеет возможность работать без интернета. Функционал фильтрации также поддерживает работу без интернета. Все вкладки поддерживают Pull-to-Refresh. Во время загрузки данных отображается индикатор выполнения. Для работы с картинками использовалась библиотека Glide. Для фрагментов, содержащих RecyclerView, созданы адаптеры, отображающие списки. Также для каждого фрагмента создана своя ViewModel, которая получает данные и обрабатывает изменения.

## Технологии
* Kotlin
* Java
* Glide
* Coroutines
* RxJava
* Retrofit
* Pagging3
* Room
* Dagger2
* MVVM, Clean architecture

## Функционал приложения
При запуске приложения запускается Splash Screen (gif animation), который символизирует название приложение по мультсериалу Rick and Morty.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/9074499f-b01b-4a69-94d1-a923421c18d7" width="200" height="400">

После загрузки Splash Screen, мы увидим главный экран с персонажами, а так же есть навигация между 3 вкладками: персонажи, локации и эпизоды. Списки были созданы спомощи RecyclerView. А данные были взяты с официальнного сайта API. Получаем данные с помощь библиотеки Retrofit. Данные списка загружаются постранично с помощью библиотеки Pagging3, и сохраняются в нашу базу данных с помощью библиотеки Room.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/30e50df6-92b4-45b8-a797-35dc4cd3d5c6" width="200" height="400">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/7533e84a-c37f-4197-8be4-753e0e1ac847" width="200" height="400">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/544e7e2c-2e0f-48ea-bb27-94e0bb49b78c" width="200" height="400">

При нажатии на персонажа мы переходим в детальную информацию о персонаже

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/81e4a365-fe48-4b92-9302-7031e26a9ebf" width="200" height="400">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/873d8156-cc01-415d-8dcb-f343a98010e8" width="200" height="400">

В деталях персонажа мы можем увидеть детальную информацию о персонаже(п.2,3,4,5,6,7). На экране реализована кнопка назад (п.1), которая возвращает нас на главный экран.
При нажатии на TextView "Происхождение" (п.6) мы переходим на детальнный экран локации. Аналогично если мы нажмем на  "Местоположение" (п7) мы перейдем на детальную информацию локации.
Также мы можем увидеть информацию в каких эпизодах присутсовал данный персонаж (п.8). При нажатии на элемент эпизода мы увидем детальную информацию эпизода.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/68e9bff2-0886-4211-9925-a39bc746c1a0" width="200" height="400">

Перейдя в детальную информацию эпизода мы увидим информацию: название эпизода (п.2), номер эпизода и его сезон(п.3) и дату выпуска (п.4). Также присутствует кнопка назад(п.1), которая при нажатии вернет нас назад в детали персонажа (откуда мы "пришли).
В пункте 5 мы видим список персонажей, которые присутствует в данном эпизоде. При выборе персонажа мы перейдем на детальный экран конкретного персонажа.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/8f9b0bb7-b949-403b-bf2e-850f7c97dd1d" width="200" height="400">

Списки с локациями и эпизодами имеют аналогичный функционал приложения.

Во всех вкладках со списками (персонажи, локации, эпизоды) мы можем использовать поиск и фильтрацию.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/3b535503-0292-4318-85db-96a66e3aa5af" width="200" height="400">

Пример поиска по фильтрации для каждого списка.

<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/0d7a596c-53cb-479f-aa4a-5c27bc68dcb9" width="200" height="400">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/d1aaeb09-c7ec-4310-8788-0997167d4f26" width="200" height="400">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/85f2dbf7-5812-41e0-bbb9-deac0015dee5" width="200" height="400">

При отсутствии интернет соединение приложение функционирует (не полностью). 

## Скриншоты
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/9074499f-b01b-4a69-94d1-a923421c18d7" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/30e50df6-92b4-45b8-a797-35dc4cd3d5c6" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/7533e84a-c37f-4197-8be4-753e0e1ac847" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/544e7e2c-2e0f-48ea-bb27-94e0bb49b78c" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/873d8156-cc01-415d-8dcb-f343a98010e8" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/dea29c64-eb9e-40a3-b779-9d3ccec4b0db" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/9cbfd7c7-f7ff-43ec-b5c8-96912456486f" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/0d7a596c-53cb-479f-aa4a-5c27bc68dcb9" width="300" height="700">
<img src="https://github.com/Grifalionys/RickandMorty/assets/112081615/85f2dbf7-5812-41e0-bbb9-deac0015dee5" width="300" height="700">
