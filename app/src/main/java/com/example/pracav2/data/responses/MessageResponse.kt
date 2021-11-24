package com.example.pracav2.data.responses

data class MessageResponse (
    val message: String,
    val timestamp: String,
    val status: Int,
    val error: String,
    val path: String
    )