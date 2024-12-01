package com.dicoding.ping.auth.otp

data class OtpResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class OtpResult(
    val createdAt: String? = null,
    val password: String? = null,
    val address: String? = null,
    val otp_code: String? = null,
    val otp_expiration: String? = null,
    val is_verified: Boolean? = null,
    val role: String? = null,
    val id: Int? = null,
    val email: String? = null,
    val username: String? = null,
    val updatedAt: String? = null
)

