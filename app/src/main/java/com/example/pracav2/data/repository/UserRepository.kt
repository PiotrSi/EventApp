package com.example.pracav2.data.repository

import com.example.pracav2.data.network.UserApi
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: UserApi
) : BaseRepository(api) {

    suspend fun getUser() = safeApiCall { api.getUser() }

    suspend fun getEvents() = safeApiCall { api.getEvents() }

}