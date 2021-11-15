package com.example.pracav2.data.network

import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.LoginResponse
import retrofit2.http.GET

interface UserApi : BaseApi{
    @GET("user")
    suspend fun getUser(): LoginResponse

    @GET("event/events")
    suspend fun getEvents():EventResponse
}
