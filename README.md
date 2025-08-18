Main branch status
[![Main Status](https://github.com/Ryqite/Tasks/workflows/android-ci.yml/badge.svg)](https://github.com/Ryqite/Tasks/actions/workflows/android-ci.yml)
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

Кратко об архитектуре проекта:
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
