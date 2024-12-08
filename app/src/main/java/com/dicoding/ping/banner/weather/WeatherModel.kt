package com.dicoding.ping.banner.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherModel(private val repository: WeatherRepository) : ViewModel() {

    val weather: LiveData<WeatherResponse> = repository.weather
    val errorMessage: LiveData<String> = repository.errorMessage

    fun fetchDataWeather() {
        viewModelScope.launch {
            repository.getDataWeather()
        }
    }
}