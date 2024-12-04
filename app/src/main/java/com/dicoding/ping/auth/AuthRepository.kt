package com.dicoding.ping.auth

import com.dicoding.ping.ApiService
import com.dicoding.ping.auth.login.LoginRequest
import com.dicoding.ping.auth.register.RegisterRequest
import com.dicoding.ping.auth.login.LoginResponse
import com.dicoding.ping.auth.otp.OtpRequest
import com.dicoding.ping.auth.otp.OtpResponse
import com.dicoding.ping.auth.otp.ResendOtpRequest
import com.dicoding.ping.auth.otp.ResendOtpResponse
import com.dicoding.ping.auth.password.ForgotPasswordRequest
import com.dicoding.ping.auth.password.ForgotPasswordResponse
import com.dicoding.ping.auth.register.RegisterResponse
import com.dicoding.ping.user.UserDataResponse

class AuthRepository(
    private val apiService: ApiService
) {
    suspend fun register(username: String, email: String, password: String, role: String): RegisterResponse {
        val request = RegisterRequest(username, email, password, role)
        return apiService.register(request)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return apiService.login(request)
    }

    suspend fun verify(email: String, otp_code: String): OtpResponse {
        val request = OtpRequest(email, otp_code)
        return apiService.verify(request)
    }

    suspend fun resendOtp(email: String): ResendOtpResponse {
        val request = ResendOtpRequest(email)
        return apiService.resendOtp(request)
    }

    suspend fun resendOtpForgotPassword(email: String): ResendOtpResponse {
        val request = ResendOtpRequest(email)
        return apiService.resendOtp(request)
    }

    suspend fun resetPassword(otp_code: String, email: String, newPassword: String): ForgotPasswordResponse {
        val request = ForgotPasswordRequest(otp_code, email, newPassword)
        return apiService.resetPassword(request)
    }

    suspend fun getUserData(token: String): UserDataResponse {
        return apiService.getUserData("Bearer $token")
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}