package com.example.pracav2.data.responses

data class EventResponseItem (
    val data_end: Any,
    val data_start: Any,
    val department: String,
    val description: String,
    val eventType: String,
    val id: Int,
    val imageData: String,
    val imageSize: Int,
    val max_number_of_contestant: Int,
    val name: String,
    val statusEvent: String
)