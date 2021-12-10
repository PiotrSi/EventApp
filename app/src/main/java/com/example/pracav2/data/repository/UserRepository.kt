package com.example.pracav2.data.repository

import com.example.pracav2.data.UserPreferences
import com.example.pracav2.data.network.UserApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi

) : BaseRepository(api) {


    suspend fun getUser() = safeApiCall { api.getUser() }

    suspend fun getEvents() = safeApiCall { api.getEvents() }

    suspend fun getUserInfo() = safeApiCall { api.getUserInfo() }

    suspend fun enroll(
        requestBody : RequestBody
    ) = safeApiCall {
        api.enroll(requestBody)
    }

    suspend fun rate(
        requestBody : RequestBody
    ) = safeApiCall {
        api.rate(requestBody)
    }
}