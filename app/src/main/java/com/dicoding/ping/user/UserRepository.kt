package com.dicoding.ping.user

import com.dicoding.ping.api.ApiService

class UserRepository(private val apiService: ApiService)  {
    suspend fun getUserData() = apiService.getUserData("Bearer {Your Token}")
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}