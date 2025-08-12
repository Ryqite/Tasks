package com.example.week7

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.example.week9.Presentation.Data.FilmsItem
import com.example.week9.Presentation.Data.ProfileData
import com.example.week9.Presentation.Screens.DetailScreen
import com.example.week9.Presentation.Screens.MainScreen
import com.example.week9.Presentation.Screens.ProfileScreen
import org.junit.Rule
import org.junit.Test
import com.example.week9.R

class GoldenTests {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light"
    )
    @Test
    fun `MainScreen Golden Test`(){
        val testFilms = listOf(
            FilmsItem(
                title = "Test title",
                imageURL = "android.resource://com.example.week9/drawable/upside_down_gray_cat",
                description = "Very good film",
                kinopoiskId = 333,
                rating = "4.9"
            )
        )
        paparazzi.snapshot {
            MaterialTheme{
                MainScreen(
                    films = testFilms,
                    navigateToProfilePage = {},
                    onSearchQueryChanged = {},
                    searchQuery = "",
                    onCancelNewSearchFilms = {},
                    navigateToDetailScreen = {}
                    )
            }
        }
    }
    @Test
    fun `DetailScreen Golden Test`(){
        val testFilm = FilmsItem(
            title = "Test title",
            imageURL = "test link",
            description = "Very good film",
            kinopoiskId = 333,
            rating = "4.9"
        )
        paparazzi.snapshot {
            MaterialTheme{
                DetailScreen(
                    certainFilms = testFilm,
                    navigateToMainScreen = {}
                )
            }
        }
    }
    @Test
    fun `ProfileScreen Golden Test`(){
        val testData = ProfileData(
        img = R.drawable.upside_down_gray_cat,
        nickName = "Fila",
        fullName = "G T O"
        )
        paparazzi.snapshot {
            MaterialTheme{
                ProfileScreen(
                    data = testData,
                    backIcon = {}
                )
            }
        }
    }
}