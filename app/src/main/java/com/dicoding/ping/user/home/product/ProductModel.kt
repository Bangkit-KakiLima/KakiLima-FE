package com.dicoding.ping.user.home.product

import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class ProductModel(private val repository: ProductRepository) : ViewModel() {

    val products: LiveData<List<DataItem>> = repository.products
    val productsRecommendations: LiveData<List<DataItem>> = repository.productsRecommendations
    val errorMessage: LiveData<String> = repository.errorMessage

    private val _productDetailLiveData = MutableLiveData<ProductDetail?>()
    val productDetailLiveData: MutableLiveData<ProductDetail?> get() = _productDetailLiveData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts()
        }
    }

    fun fetchAllProductsRecommendations() {
        viewModelScope.launch {
            repository.getAllRecommendationProducts()
        }
    }

    fun fetchProductDetail(id: Int) {
        _loading.value = true
        viewModelScope.launch {
            try {
                _productDetailLiveData.value = null // Menampilkan loading data
                val response = repository.getProductById(id)
                if (response.isSuccessful) {
                    _productDetailLiveData.value = response.body()
                } else {
                    // Handle error
                    _productDetailLiveData.value = null
                }
            } catch (e: Exception) {
                // Handle exception
                _productDetailLiveData.value = null
//            } finally {
//                _loading.value = false
            }
        }
    }
}

