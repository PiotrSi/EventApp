package com.example.pracav2.data.responses

data class LoginResponse(
    val refreshToken: String?,
    val accessToken: String?,
    val companyName: Any,
    val companyNip: Any,
    val department: Any,
    val id: Int,
    val phone: Any,
    val roles: List<String>,
    val tokenType: String,
    val username: String,
    val place: String
)