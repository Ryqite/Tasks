package com.example.week9.Presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9.Domain.GetLatestNewsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * ViewModel для работы с новостями.
 *
 * Наследуется от [AndroidViewModel] для доступа к контексту приложения.
 *
 * @param getLatestNews Use case для получения списка новостей
 * @property _latestNews Приватное изменяемое состояние списка новостей,
 * используется как источник данных для [latestNews]
 * @property latestNews Публичное неизменяемое состояние списка новостей,
 * предоставляет подписку на обновления списка новостей.
 * функция [loadNewsItems] вызывает функцию [getLatestNews] в [GetLatestNewsUseCase],
 * где каждый [News] преобразуется в [NewsItem] и результат сохраняется в переменнную [_latestNews]
 */
class NewsViewModel(
    private val getLatestNews: GetLatestNewsUseCase
) : ViewModel() {
    private val _latestNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val latestNews: StateFlow<List<NewsItem>> = _latestNews
    init {
        loadNewsItems()
    }
    private fun loadNewsItems(){
        viewModelScope.launch {
            try {
//            throw Exception("Сообщение об ошибке")
            _latestNews.value = getLatestNews().map { it.toNewsItem() }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }
    private fun handleError(e: Exception) {
        when (e) {
            is IOException -> Log.e("ErrorHandler","Ошибка сети: ${e.message}")
            is HttpException -> when (e.code()) {
                401 -> Log.e("ErrorHandler","Требуется авторизация")
                403 -> Log.e("ErrorHandler","Доступ запрещен")
                404 -> Log.e("ErrorHandler","Новости не найдены")
                in 500..599 -> Log.e("ErrorHandler","Ошибка сервера")
                else -> Log.e("ErrorHandler","HTTP ошибка: ${e.code()}")
            }
            else -> Log.e("ErrorHandler","Неизвестная ошибка")
        }
    }
}