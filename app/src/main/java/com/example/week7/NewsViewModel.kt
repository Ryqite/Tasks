package com.example.week7

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7.Data.News
import com.example.week7.Mappers.toNews
import com.example.week7.Network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        val result = RetrofitInstance.api.getLatestNews()
        _latestNews.value = result.articles.map { it.toNews() }
    }

}