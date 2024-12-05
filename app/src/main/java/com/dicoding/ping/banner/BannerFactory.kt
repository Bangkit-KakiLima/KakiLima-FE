package com.dicoding.ping.banner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BannerFactory(private val repository: BannerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BannerModel::class.java)) {
            return BannerModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
