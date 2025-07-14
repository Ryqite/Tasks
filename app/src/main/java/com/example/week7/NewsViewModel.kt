package com.example.week7

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.week7.Data.News
import com.example.week7.Data.NewsResponse
import com.example.week7.Mappers.toNews
import com.example.week7.Network.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

/**
 * ViewModel для работы с новостями.
 *
 * Наследуется от [AndroidViewModel] для доступа к контексту приложения.
 *
 * @param application Контекст приложения, необходимый для [AndroidViewModel]
 * @property _latestNews Приватное изменяемое состояние списка новостей,
 * используется как источник данных для [latestNews]
 * @property latestNews Публичное неизменяемое состояние списка новостей,
 * предоставляет подписку на обновления списка новостей.
 * функция [getLatestNews] вызывает функцию [getLatestNews] в [NewsAPI]
 * и сохраняет результат в переменнную [result], дальше в [result]
 * каждый [article] преобразуется в [News] и итоговый список сохраняется в [_latestNews]
 */
class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val _latestNews = MutableStateFlow<List<News>>(emptyList())
    val latestNews: StateFlow<List<News>> = _latestNews.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        getLatestNews()
    }

    fun getLatestNews() = viewModelScope.launch {
        try {
            val result = RetrofitInstance.api.getLatestNews()
            _latestNews.value = result.articles.map { it.toNews() }
            _errorMessage.value = null

        }catch (e: Exception) {
            handleError(e)        }
    }
    private fun handleError(e: Exception) {
        _errorMessage.value = when (e) {
            is IOException -> "Ошибка сети: ${e.message}"
            is HttpException -> when (e.response.code) {
                401 -> "Требуется авторизация"
                403 -> "Доступ запрещен"
                404 -> "Новости не найдены"
                in 500..599 -> "Ошибка сервера"
                else -> "HTTP ошибка: ${e.response.code}"
            }
            else -> "Неизвестная ошибка"
        }
    }
    fun clearError() {
        _errorMessage.value = null
    }
}