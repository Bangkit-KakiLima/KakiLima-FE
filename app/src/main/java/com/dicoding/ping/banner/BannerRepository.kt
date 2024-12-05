package com.dicoding.ping.banner

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.ping.api.ApiService

class BannerRepository(private val apiService: ApiService) {
    private val _banners = MutableLiveData<List<Weather>>()
    val banners: LiveData<List<Weather>> = _banners

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val weatherData = listOf(
        Weather(
            id = 1,
            category = "Hujan",
            description = "Cuaca hujan bikin suasana lebih nyaman, ayo lengkapi harimu dengan yang terbaik! Jangan sampai terlewat.",
            imageUrl = "https://i.pinimg.com/736x/7a/44/19/7a44199aff3fd42c45b1807feb518fa4.jpg"
        ),
        Weather(
            id = 2,
            category = "Mendung",
            description = "Langit mendung, tapi semangat tetap harus cerah! Buat harimu lebih seru dengan hal istimewa ini!",
            imageUrl = "https://i.pinimg.com/736x/28/85/71/28857188f7a6757d5dba9d3f339f1bec.jpg"
        ),
        Weather(
            id = 3,
            category = "Panas",
            description = "Cuaca panas? Waktunya cari sesuatu yang bikin segar dan nyaman. Yuk, jangan tunggu lama-lama!",
            imageUrl = "https://i.pinimg.com/736x/b9/39/79/b939794266cf899fbbd55435efd2a402.jpg"
        ),
        Weather(
            id = 4,
            category = "Cerah",
            description = "Matahari bersinar terang, saatnya menikmati hari dengan penuh semangat. Temukan pilihan yang bikin harimu lebih spesial!",
            imageUrl = "https://i.pinimg.com/736x/ff/08/a6/ff08a64470b936483bc51b823cb726eb.jpg"
        )
    )

    fun getAllData() {
        try {
            _banners.postValue(weatherData)
        } catch (e: Exception) {
            _errorMessage.postValue("Error: ${e.message}")
            Log.e("BannerReposotory", "getAllData on Exception: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: BannerRepository? = null
        fun getInstance(
            apiService: ApiService
        ): BannerRepository =
            instance ?: synchronized(this) {
                instance ?: BannerRepository(apiService)
            }.also { instance = it }
    }
}