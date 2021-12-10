package com.example.pracav2.data.responses

data class UserInfoResponse (
    val id: Int,
    val username: String,
    val department: String,
    val numberSignedEvents: Int,
    val averageRate: Float

)