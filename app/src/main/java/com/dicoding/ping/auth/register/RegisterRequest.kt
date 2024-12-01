package com.dicoding.ping.auth.register

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String
)
