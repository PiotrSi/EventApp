package com.example.pracav2.data.network

import com.example.pracav2.data.responses.LoginResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi : BaseApi {


    @POST("auth/signin")
    suspend fun login(
        @Body requestBody: RequestBody
    ): LoginResponse
}
