package com.example.pracav2.data.network

import com.example.pracav2.data.responses.EventResponse
import com.example.pracav2.data.responses.MessageResponse
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApi : BaseApi{
//    var userpreferences: UserPreferences



//    @Headers("Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb3NzcyIsImlhdCI6MTYzNzY2MTg4OH0.xX5dz5S4acSnnPRHVgF7Pu0MPcF6v3O7QD0Di9SnaFZg4_KClGsvBY8xMbdCTksdRz1EfRC2Hi0K8d1UIDU1-g")
    @GET("test/user")
    suspend fun getUser():MessageResponse

//    @Headers("Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjb3NzcyIsImlhdCI6MTYzNzY2MTg4OH0.xX5dz5S4acSnnPRHVgF7Pu0MPcF6v3O7QD0Di9SnaFZg4_KClGsvBY8xMbdCTksdRz1EfRC2Hi0K8d1UIDU1-g")
    @GET("event/events")
    suspend fun getEvents(@Header("adsadsa") token : String):EventResponse



    @POST("event/enroll")
    suspend fun  enroll(
        @Body requestBody: RequestBody
    ):MessageResponse

}
