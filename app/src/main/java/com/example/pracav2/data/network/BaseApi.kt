package com.example.pracav2.data.network

import com.example.pracav2.data.responses.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST

interface BaseApi {
    @POST("logout")
    suspend fun logout(): ResponseBody

//    @GET("event/user")
//    suspend fun logout(): UserInfoResponse
}