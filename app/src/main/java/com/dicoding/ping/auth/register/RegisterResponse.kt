package com.dicoding.ping.auth.register

data class RegisterResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Result(
    val id: Int? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val otp_code: String? = null,
    val otp_expiration: String? = null,
    val is_verified: Boolean? = null,
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val address: String? = null,

    )

