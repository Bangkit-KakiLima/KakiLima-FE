package com.dicoding.ping.user.home.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ping.api.ApiService

class ProductRepository(private val apiService: ApiService) {

    private val _products = MutableLiveData<List<DataItem>>()
    val products: LiveData<List<DataItem>> = _products

    private val _productsRecommendations = MutableLiveData<List<DataItem>>()
    val productsRecommendations: LiveData<List<DataItem>> = _productsRecommendations

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    suspend fun getAllProducts() {
        try {
            val response = apiService.getAllProducts()
            Log.d("ProductRepository", "getAllData on Response: $response")
            _products.postValue(response.data as List<DataItem>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllData on Exception: ${e.message}")
        }
    }

    suspend fun getAllRecommendationProducts() {
        try {
            val response = apiService.getAllRecommendationProducts()
            Log.d("ProductRepository", "getAllData on Response: $response")
            _productsRecommendations.postValue(response.data as List<DataItem>?)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("ProductRepository", "getAllData on Exception: ${e.message}")
        }
    }
}