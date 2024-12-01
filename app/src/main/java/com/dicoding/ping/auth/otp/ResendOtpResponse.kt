package com.dicoding.ping.auth.otp

data class ResendOtpResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Result(
    val otp_expiration: String? = null,
    val id: Int? = null,
    val otp_code: String? = null,
    val is_verified: Boolean? = null,
    val email: String? = null,
    val username: String? = null
)

