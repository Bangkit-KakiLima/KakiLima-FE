package com.dicoding.ping.user.locations

import com.dicoding.ping.api.ApiService

class LocationRepository(private val apiService: ApiService)  {
    suspend fun getAllLocations() = apiService.getAllLocations()
    companion object {
        @Volatile
        private var instance: LocationRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LocationRepository =
            instance ?: synchronized(this) {
                instance ?: LocationRepository(apiService)
            }.also { instance = it }
    }
}