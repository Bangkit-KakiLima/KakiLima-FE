package com.dicoding.ping.api

import android.content.Context
import com.dicoding.ping.utils.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private lateinit var sessionManager: SessionManager
    private const val BASE_URL = "https://kakilima-be-780530589771.asia-southeast2.run.app/api/"

    fun initialize(context: Context) {
        sessionManager = SessionManager(context)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        createRetrofit().create(ApiService::class.java)
    }
}