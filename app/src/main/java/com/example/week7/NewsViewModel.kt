package com.example.week7

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.week7.Domain.News
import com.example.week7.Data.Mappers.toNews
import com.example.week7.Data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    init {
        getLatestNews()
    }

    fun getLatestNews() = viewModelScope.launch {
        try {
//            throw Exception("Сообщение об ошибке")
            val result = RetrofitInstance.api.getLatestNews()
            _latestNews.value = result.articles.map { it.toNews() }

        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun handleError(e: Exception) {
        when (e) {
            is IOException -> Log.e("ErrorHandler","Ошибка сети: ${e.message}")
            is HttpException -> when (e.response.code) {
                401 -> Log.e("ErrorHandler","Требуется авторизация")
                403 -> Log.e("ErrorHandler","Доступ запрещен")
                404 -> Log.e("ErrorHandler","Новости не найдены")
                in 500..599 -> Log.e("ErrorHandler","Ошибка сервера")
                else -> Log.e("ErrorHandler","HTTP ошибка: ${e.response.code}")
            }

            else -> Log.e("ErrorHandler","Неизвестная ошибка")
        }
    }

}