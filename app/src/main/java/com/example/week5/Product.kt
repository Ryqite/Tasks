package com.example.week5

data class Product(
    val id: Int = 0,
    val title: String = "",
    val photoUri: String = "",
    val content: String = ""
)
var testProducts = listOf(
    Product(
        id = 1,
        title = "Смартфон Galaxy S23",
        photoUri = "https://www.samsungstore.ru/_next/image/?url=https%3A%2F%2Fcdn.samsungstore.ru%2Fiblock%2Ff89%2Ff89005113fc4a94e95ec29e6569aa810%2Fba0adfa23f3c27e93226fe55c4ce03d9.jpg&w=1080&q=75",
        content = "Флагманский телефон с камерой 200 МП и процессором Snapdragon 8 Gen 2."
    ),
    Product(
        id = 2,
        title = "Ноутбук MacBook Pro",
        photoUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQToKAC7v91vqZbSABVjcNRfBkZwy55GqZbAQ&s",
        content = "14-дюймовый экран, чип M2 Pro, 16 ГБ оперативной памяти."
    ),
    Product(
        id = 3,
        title = "Наушники Sony WH-1000XM5",
        photoUri = "https://imgproxy.onliner.by/sRsYgHZ8JmQym4R3BXHpwR4ZshbXQ46Vy4AcH8uLhKk/w:170/h:250/z:2/f:jpg/aHR0cHM6Ly9jb250/ZW50Lm9ubGluZXIu/YnkvY2F0YWxvZy9k/ZXZpY2Uvb3JpZ2lu/YWwvOTRhNzIyMDk4/NWJlODkwYzAwNjJk/MTg5NWMyMzE0Nzcu/anBlZw",
        content = "Беспроводные наушники с шумоподавлением и 30-часовой работой."
    ),
    Product(
        id = 4,
        title = "Фотоаппарат Canon EOS R5",
        photoUri = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVzIdiL-k95-9u4D2g17fkzer1NWzo-fVj6w&s",
        content = "Зеркальная камера с 8K видео и стабилизацией 5 оси."
    ),
    Product(
        id = 5,
        title = "Умные часы Apple Watch Ultra",
        photoUri = "https://imgproxy.onliner.by/x-B8Ur1A4HxaZgE3R7qmCNfHfCgZO3TC8y4xQxsqA2k/w:170/h:250/z:2/f:jpg/aHR0cHM6Ly9jb250/ZW50Lm9ubGluZXIu/YnkvY2F0YWxvZy9k/ZXZpY2Uvb3JpZ2lu/YWwvY2IyMDkyNjZh/YTRhZThjYTgzODM2/NjhmOTc2ZWYyMGUu/anBn",
        content = "Водонепроницаемые часы с GPS и мониторингом ЭКГ."
    )
)
