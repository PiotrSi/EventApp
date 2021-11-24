package com.example.pracav2.data.responses

data class EventResponseItem (
    val id: Int,
    val czyMoznaOceniac: Boolean,
    val czyMoznaZapisac: Boolean,
    val czyZapisano: Boolean,
    val name: String,
    val data_end: String,
    val data_start: String,
    val department: String,
    val description: String,
    val eventType: String,
    val imageSize: Int,
    val max_number_of_contestant: Int,
    val rate: Int,
    val statusEvent: String,  //Zaakceptowany
    val imageData: String
)