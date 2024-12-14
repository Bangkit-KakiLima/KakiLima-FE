package com.dicoding.ping.auth.register

import kotlinx.coroutines.launch


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.ping.auth.AuthRepository
import com.dicoding.ping.utils.SessionManager
import retrofit2.HttpException

class RegisterModel(private val repository: AuthRepository) : ViewModel() {

    private lateinit var sessionManager: SessionManager
    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    fun register(
        username: String,
        email: String,
        password: String,
        role: String,
        onResult: (Boolean) -> Unit
    ) {
        val TAG = "registerUser"
        Log.d(TAG, "registerUser: $username, $email, $password")
        viewModelScope.launch {
            try {
                val response: RegisterResponse =
                    repository.register(username, email, password, role)
                if (response.success == true) {
                    Log.d(TAG, "Registration successful: ${response.message}")
                    Log.d("RegisterModel", "register: ${response.result}")
                    onResult(true)
                } else {
                    Log.d(TAG, "Registration failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP Error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
                onResult(false)
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected Error", e)
                onResult(false)
            }
        }

    }
}
