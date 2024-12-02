package com.dicoding.ping.user

data class UserDataResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Result(
    val createdAt: String? = null,
    val address: Any? = null,
    val role: String? = null,
    val id: Int? = null,
    val is_verified: Boolean? = null,
    val email: String? = null,
    val username: String? = null,
    val updatedAt: String? = null
)

