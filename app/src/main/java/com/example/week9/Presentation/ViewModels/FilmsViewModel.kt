package com.example.week9.Presentation.ViewModels

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week9.Domain.UseCases.GetLatestFilmsUseCase
import com.example.week9.Presentation.Data.FilmsItem
import com.example.week9.Presentation.Mappers.toFilmsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

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
@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getLatestFilms: GetLatestFilmsUseCase
) : ViewModel() {
    private val _latestfilms = MutableStateFlow<List<FilmsItem>>(emptyList())
    val latestfilms: StateFlow<List<FilmsItem>> = _latestfilms

    private val _searchQuery = MutableStateFlow("f")
    val searchQuery: StateFlow<String> = _searchQuery

    private var searchJob: Job? = null

    init {
        startSearchCollector()
    }

    private fun startSearchCollector() {
        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(2000)
                .retry(3) { e->
                    Log.e("CollectorErrorRetry", e.message?:"неизвестная ошибка")
                    e is Exception
                }
                .catch {e->
                    Log.e("CollectorError", e.message?:"неизвестная ошибка")
                }
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) loadfilmsItems(query)
                }
        }
    }

    private fun loadfilmsItems(query: String) {
        viewModelScope.launch {
            try {
                _latestfilms.value = getLatestFilms(query).map { it.toFilmsItem() }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun cancelSearch(){
        searchJob?.cancel()
        _searchQuery.value = ""
        startSearchCollector()
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