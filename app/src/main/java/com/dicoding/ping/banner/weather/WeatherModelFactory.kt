package com.dicoding.ping.banner.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeatherModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherModel::class.java)) {
            return WeatherModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}