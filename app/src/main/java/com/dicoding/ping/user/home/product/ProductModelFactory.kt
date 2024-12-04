package com.dicoding.ping.user.home.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductModel::class.java)) {
            return ProductModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

