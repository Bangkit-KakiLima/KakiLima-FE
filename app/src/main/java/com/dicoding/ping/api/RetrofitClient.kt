package com.dicoding.ping.api

import android.content.Context
import com.dicoding.ping.utils.SessionManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private lateinit var sessionManager: SessionManager
    private const val BASE_URL = "http://35.219.16.190:3000/api/"
    private const val BASE_IP = "http://35.219.16.190:3000"

    fun getBaseIp(): String {
        return BASE_IP
    }

    fun initialize(context: Context) {
        sessionManager = SessionManager(context)
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
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