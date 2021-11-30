package com.example.pracav2.data.network

import com.example.pracav2.data.responses.DepResponse
import com.example.pracav2.data.responses.LoginResponse
import com.example.pracav2.data.responses.MessageResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi : BaseApi {


    @POST("auth/signin")
    suspend fun login(
        @Body requestBody: RequestBody
    ): LoginResponse

    @POST("auth/signup")
    suspend fun signup(
        @Body requestBody: RequestBody
    ): MessageResponse

    @GET("org/departments")
    suspend fun getDep(): DepResponse
}
