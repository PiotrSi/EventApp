package com.example.pracav2.data.repository

import com.example.pracav2.data.network.BaseApi
import com.example.pracav2.data.network.SafeApiCall

abstract class BaseRepository(private val api: BaseApi) : SafeApiCall {

    suspend fun logout() = safeApiCall {
        api.logout()
    }
}