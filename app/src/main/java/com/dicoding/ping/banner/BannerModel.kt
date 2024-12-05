package com.dicoding.ping.banner

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BannerModel(private val repository: BannerRepository) : ViewModel() {

    val banners: LiveData<List<Weather>> = repository.banners
    val errorMessage: LiveData<String> = repository.errorMessage

    fun fetchAllProducts() {
        viewModelScope.launch {
            repository.getAllData()
        }
    }
}