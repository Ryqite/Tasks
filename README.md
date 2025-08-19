Main branch status
[![Main CI Pipeline](https://github.com/Ryqite/Tasks/actions/workflows/android-ci.yml/badge.svg?branch=main)](https://github.com/Ryqite/Tasks/actions/workflows/android-ci.yml)
----
#Описание проекта:

BooksApp - андроид проект, служащий для удобного поиска нужных книг. Оно позволяет искать нужные книги по 
ключевым словам в поиске, дальнейший просмотр, сохранение и просмотр избранных книг.
Также есть реализация профиля пользователя с созданием и входом в профиль, а также настройки самого приложения такие как: смена языка и темы приложения.
----
Проект соблюдает: принципы Clean Architecture, SOLID, а также реализует паттерн MVVM.
Проект включает в себя использование: 
-Room(для базы данных) 
-Hilt(для DI) 
NavigationComponent(для навигации по приложению) 
-Retrofit и OkHttp(для работы с сетью)
-JUnit,MockK,Kotlinx Coroutines Test, Compose-Testing(для тестирования приложения)
-Jetpack Compose(для UI)
----
Кратко об структуре проекта:

1.Data слой (хранит всё, что связано с данными):
-Local - работа с базой данных (Room)
-Remote - работа с API (Retrofit)
-Repository - реализация репозиториев

Domain слой (содержит бизнес-правила приложения):
-Модели данных 
-Интерфейсы репозиториев
-UseCases - отдельные операции (например, получение списка фильмов)

Presentation слой (отвечает за отображение информации):
-ViewModel - готовит данные для экрана
-Activity/Fragment/Composables - отображает UI

Архитектура проекта:
----
## 1. _Domain_
Содержит:
* интерфейсы репозитория `BooksNetworkRepository`, который будет получать данные из сети, `BooksDatabaseRepository` который будет получать локальные данные из базы данных,
* `SettingsRepository`, который будет получать данные хранилищ DataStore и SharedPreferences
* бизнес-операции, которые представляются в виде `UseCase` и вынесены в отдельный класс (исп. operator fun invoke так что вызываются как функции),
принимают:
-репозиторий `BooksNetworkRepository` как параметр и являются делегатами его работы для `NetworkViewModel` в _Presentation_ слое
-репозиторий `BooksDatabaseRepository` как параметр и являются делегатами его работы для `DatabaseViewModel` в _Presentation_ слое
-репозиторий `SettingsRepository` как параметр и являются делегатами его работы для `SettingsViewModel` в _Presentation_ слое

* главные модели данных `ProfileParametersData`, `BooksFromDatabase`, `BooksFromNetwork`, `AppTheme`, используемые для бизнес-логики
-----
### _ Файлы: _
* `BooksDatabaseRepository`, `BooksNetworkRepository`, `SettingsRepository`, `ChangeDarkThemeUseCase`, `DeleteBookUseCase`,
* `GetAllBooksUseCase`, `GetAllUsersUseCase`, `GetBooksBySearchUseCase`, `GetLanguageUseCase`,  `InsertNewBookUseCase`,
* `InsertNewUserUseCase`, `SaveLanguageUseCase`, `SetAppLocaleUseCase`, `UpdateBookUseCase`,  `BooksFromDatabase`,
* `AppTheme`,  `BooksFromNetwork`, `ProfileParametersData`

## 2. _Data_
Содержит:
* Локальные данные:
* -Preferences: `AppSharedPreferences`, `SharedPreferencesDataSourc`e, `SharedPreferencesDataSourceImpl`
* -DataStore: `AppDataStore`, `DataStoreSource`, `DataStoreSourceImpl`
* -База данных: `BooksDatabase`, `BooksDao`, `LocalDataSource`, `LocalDataSourceImpl`
* Удаленные данные
* -API: `BooksAPI`
* -Сетевые компоненты: `RemoteDataSource`, `RemoteDataSourceImpl`, `RetryInterceptor`
* Модели: `BooksDbModel`, `Profile`, `BooksResponse`, `Item`, `VolumeInfo`
* Мапперы
* -`BooksDbModel.toBooksFromDatabase`, `BooksFromDatabase.toBooksDbModel`, `Profile.toProfileParametersData`, `ProfileParametersData.toProfile`, `VolumeInfo.toBooksFromNetwork`
* Репозитории
* -`BooksDatabaseRepositoryImpl`, `BooksNetworkRepositoryImpl`, `SettingsRepositoryImpl`
* Утилиты:
* `Constants`

Репозитории использует источники данных с моделями для получения данных локально и удаленно, преображают их с помощью мапперов,
а также реализуют интерфейсы и используются в юз-кейсах из _Domain-слоя_
-----
### _ Файлы: _
* `BooksDbModel`, `Profile`, `BooksResponse`, `Item`, `VolumeInfo`, `BooksDbModel.toBooksFromDatabase`
* `BooksFromDatabase.toBooksDbModel`, `Profile.toProfileParametersData`, `ProfileParametersData.toProfile`, `VolumeInfo.toBooksFromNetwork`,
* `AppSharedPreferences`, `SharedPreferencesDataSource`, `SharedPreferencesDataSourceImpl`, `AppDataStore`, `DataStoreSource`, `DataStoreSourceImpl`, `BooksDao`
* `BooksDatabase`, `LocalDataSource`, `LocalDataSourceImpl`, `BooksAPI`, `RemoteDataSource`
* `RetryInterceptor`, `RemoteDataSourceImpl`, `BooksDatabaseRepositoryImpl`, `BooksNetworkRepositoryImpl`, `SettingsRepositoryImpl`, `Constants`

## 3. _Presentation_
Содержит:
* composable-функции (`MainScreen`,`ProfileScreen`,`DetailScreen`,`DetailSavedScreen`,`SavedScreen`), которые отображают UI и автомотически рекомпозируются под изменением данных
* модели данных `BooksNetworkItem`, `BooksDatabaseItem`, `ProfileData` для использования в UI
* мапперы-расширения `BooksDatabaseItem.toBooksFromDatabase`, `BooksFromDatabase.toBooksDatabaseItem`, `BooksFromNetwork.toBooksNetworkItem`, `BooksNetworkItem.toBooksDatabaseItem`
* `ProfileData.toProfileParametersData`, `ProfileParametersData.toProfileData` преобразующие модели из _Domain_ слоя в модели данных _Presentation_ слоя `
* `DatabaseViewModel`, `NetworkViewModel`, `SettingsViewModel` для хранения, управления состоянием UI в виде StateFlow, а также вызовов юз-кейсов и обрабатывания ошибки при попытке получить из них данных
* utils `NavigationScreens`, `SemanticsKey` для хранения объектов навигации (точек навигации) и облегчения тестов
* навигацию в активити `MainActivity`, реализованной через navController и navHost
------
### _ Файлы: _
* `MainScreen`,`ProfileScreen`,`DetailScreen`, `DetailSavedScreen`, `SavedScreen`, `BooksNetworkItem`,
* `BooksDatabaseItem`, `ProfileData`, `BooksDatabaseItem.toBooksFromDatabase`
* `BooksFromDatabase.toBooksDatabaseItem`, `BooksFromNetwork.toBooksNetworkItem`, `BooksNetworkItem.toBooksDatabaseItem`,
*  `ProfileData.toProfileParametersData`, `ProfileParametersData.toProfileData`
* `DatabaseViewModel`, `NetworkViewModel`, `SettingsViewModel`
* `NavigationScreens`,`SemanticsKey`,`MainActivity`
---
Тестирование:
Compose Testing:
------
Покрыл все виджеты тестами на подобии "composeTestRule.onNodeWithTag("Card").assertIsDisplayed()", также проверил все иконки в приложении
и навигацию между экранами с помощью проверок наличия элементов после перехода на другой экран по типу
"composeTestRule.onNodeWithContentDescription("ProfileIcon").performClick()
 composeTestRule.onNodeWithContentDescription("Avatar").assertIsDisplayed()"
Тестами покрыты все виджеты и использующие их экраны в директории UIComponents и Screens
------
MockK/JUnit/Kotlin Coroutines Test:
-----
Использовал mock обьекты для замены реальных зависимостей вместе с coEvery и coVerify для асинхронных методов
при тесте юз-кейсов(MockK), а также runTest из Kotlin Coroutines Test для запуска coEvery и coVerify
Тестами покрыты все юз-кейсы
-------
Ветки в локальном репозитории:
<img width="974" height="631" alt="image" src="https://github.com/user-attachments/assets/2ce8906c-6469-4219-b38b-2819767f3b72" />
<img width="951" height="896" alt="image" src="https://github.com/user-attachments/assets/fc08194c-e204-4595-9bea-f4f15e073210" />
p.s. ниже показано что каждая ветка постепенно вносила изменения в итоговую ветку _main_
<img width="974" height="563" alt="image" src="https://github.com/user-attachments/assets/b32ae847-880f-4a43-88ab-6c23cb6457cf" />
<img width="974" height="617" alt="image" src="https://github.com/user-attachments/assets/297930a5-5673-44ea-b34e-ce901219981a" />
----
Инструкции по запуску:
1. скопировать проект;
2. зайти в local.properties.example и скопировать строчку API_KEY в local.properties;
3. поменять значение API_KEY в local.properties на ваш API ключ тут [Goodle Apies](https://console.cloud.google.com/apis/credentials?hl=ru&inv=1&invt=Ab52eA&project=books-468915)
