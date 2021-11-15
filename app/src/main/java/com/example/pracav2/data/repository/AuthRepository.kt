package com.example.pracav2.data.repository

import com.example.pracav2.data.UserPreferences
import com.example.pracav2.data.network.AuthApi
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository(api) {

    suspend fun login(
        requestBody : RequestBody
    ) = safeApiCall {
        api.login(requestBody)
    }

    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
        preferences.saveAccessTokens(accessToken, refreshToken)
    }

}