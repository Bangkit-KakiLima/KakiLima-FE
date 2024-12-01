package com.dicoding.ping.auth.otp

data class OtpRequest(
    val email: String,
    val otp_code: String,
)
