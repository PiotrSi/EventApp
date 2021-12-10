package com.example.pracav2.data.responses

import java.util.*

data class EventResponseItem (
    val id: Int,
    val czyMoznaOceniac: Boolean,
    val czyMoznaZapisac: Boolean,
    val czyZapisano: Boolean,
    val name: String,
    val data_start: Date,
    val data_end: Date,
    val department: String,
    val description: String,
    val eventType: String,
    val imageSize: Int,
    val max_number_of_contestant: Int,
    val rate: Float,
    val statusEvent: String,  //Zaakceptowany
    val imageData: String?
)