Compose Testing:
------
Покрыл все виджеты тестами на подобии "composeTestRule.onNodeWithTag("Card").assertIsDisplayed()", также проверил все иконки в приложении
и навигацию между экранами с помощью проверок наличия элементов после перехода на другой экран по типу
"composeTestRule.onNodeWithContentDescription("ProfileIcon").performClick()
 composeTestRule.onNodeWithContentDescription("Avatar").assertIsDisplayed()"
------
Mockito/MockK/JUnit/Kotlin Coroutines Test:
-----
Использовал mock обьекты для замены реальных зависимостей вместе с coEvery и coVerify для асинхронных методов
при тесте юз-кейсов(Mockito и MockK), а также runTest из Kotlin Coroutines Test для запуска coEvery и coVerify
Покрыл оба теста
-------
Инструкции:
1 проверить UI тесты в src/androidTest (UITest)
2 проверить тесты юз-кейсов в src/test[UnitTest] (GetLatestFilmsUseCaseTest и GetProfileDataUseCaseTest)
