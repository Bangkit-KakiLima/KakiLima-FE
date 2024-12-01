package com.dicoding.ping.auth.password

data class ForgotPasswordRequest(
    val otp_code: String,
    val email: String,
    val newPassword: String
)

