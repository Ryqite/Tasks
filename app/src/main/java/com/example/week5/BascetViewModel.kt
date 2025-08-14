package com.example.week5

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.week5.Data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BascetViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun addProduct(product: Product) {
        _products.value += product
    }
    fun removeFirstProductById(productId: Int) {
        val index = _products.value.indexOfFirst { it.id == productId }
            _products.value = _products.value.toMutableList().apply { removeAt(index) }
    }
}
