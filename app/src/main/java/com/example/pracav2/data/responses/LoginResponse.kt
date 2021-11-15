package com.example.pracav2.data.responses

data class LoginResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val email: String,
    val id: Int,
    val roles: List<String>,
    val tokenType: String,
    val username: String
)