package com.dicoding.ping.auth.login

data class LoginResponse(
    val result: Result? = null,
    val success: Boolean? = null,
    val message: String? = null
)

data class Result(
    val token: String? = null
)

