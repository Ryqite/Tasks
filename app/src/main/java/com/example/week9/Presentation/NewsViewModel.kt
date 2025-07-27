package com.example.week9.Presentation

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9.Domain.GetLatestFilmsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * ViewModel для работы с новостями.
 *
 * Наследуется от [AndroidViewModel] для доступа к контексту приложения.
 *
 * @param getLatestFilms Use case для получения списка новостей
 * @property _latestfilms Приватное изменяемое состояние списка новостей,
 * используется как источник данных для [latestfilms]
 * @property latestfilms Публичное неизменяемое состояние списка новостей,
 * предоставляет подписку на обновления списка новостей.
 * функция [loadfilmsItems] вызывает функцию [getLatestFilms] в [GetLatestFilmsUseCase],
 * где каждый [films] преобразуется в [FilmsItem] и результат сохраняется в переменнную [_latestfilms]
 */
@OptIn(FlowPreview::class)
class filmsViewModel(
    private val getLatestFilms: GetLatestFilmsUseCase
) : ViewModel() {
    private val _latestfilms = MutableStateFlow<List<FilmsItem>>(emptyList())
    val latestfilms: StateFlow<List<FilmsItem>> = _latestfilms

    private val _searchQuery = MutableStateFlow("f")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _cancelNeed = MutableStateFlow(false)
    val cancelNeed: StateFlow<Boolean> = _cancelNeed

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(2000)
                .distinctUntilChanged()
                .collect { query ->
                    loadfilmsItems(query)
                }
        }
    }

    private fun loadfilmsItems(query: String) {
        viewModelScope.launch {
            try {
//            throw Exception("Сообщение об ошибке")
                if (_cancelNeed.value)
                {
                    _latestfilms.value
                    _cancelNeed.value = false
                }
                else _latestfilms.value = getLatestFilms(query).map { it.toFilmsItem() }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCancelNewSearchFilms() {
        _cancelNeed.value = true
    }

    private fun handleError(e: Exception) {
        when (e) {
            is IOException -> Log.e("ErrorHandler", "Ошибка сети: ${e.message}")
            is HttpException -> when (e.code()) {
                401 -> Log.e("ErrorHandler", "Требуется авторизация")
                403 -> Log.e("ErrorHandler", "Доступ запрещен")
                404 -> Log.e("ErrorHandler", "Новости не найдены")
                in 500..599 -> Log.e("ErrorHandler", "Ошибка сервера")
                else -> Log.e("ErrorHandler", "HTTP ошибка: ${e.code()}")
            }

            else -> Log.e("ErrorHandler", "Неизвестная ошибка: ${e.message}")
        }
    }
}