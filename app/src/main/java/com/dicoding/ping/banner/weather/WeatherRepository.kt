package com.dicoding.ping.banner.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ping.api.ApiService

class WeatherRepository(private val apiService: ApiService) {
    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    suspend fun getDataWeather() {
        try {
            Log.d("WeatherRepository", "getAllData: ")
            val response = apiService.getWeather()
            Log.d("WeatherRepository", "getAllData: $response")
            _weather.postValue(response)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("WeatherRepository", "getAllData on Exception: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: WeatherRepository? = null
        fun getInstance(
            apiService: ApiService
        ): WeatherRepository =
            instance ?: synchronized(this) {
                instance ?: WeatherRepository(apiService)
            }.also { instance = it }
    }
}