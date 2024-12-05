package com.dicoding.ping.user.locations

import com.dicoding.ping.api.ApiService

class LocationsRepository(private val apiService: ApiService)  {
    suspend fun getAllLocations() = apiService.getAllLocations()
    companion object {
        @Volatile
        private var instance: LocationsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LocationsRepository =
            instance ?: synchronized(this) {
                instance ?: LocationsRepository(apiService)
            }.also { instance = it }
    }
}