package com.dicoding.ping.user.home.product

import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class ProductModel(private val repository: ProductRepository) : ViewModel() {

    val products: LiveData<List<DataItem>> = repository.products
    val errorMessage: LiveData<String> = repository.errorMessage

    fun fetchAllProducts() {
        viewModelScope.launch {
            repository.getAllData()
        }
    }
}

