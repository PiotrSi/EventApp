package com.example.pracav2.data.network

import android.content.Context
import android.widget.Toast
import com.example.pracav2.data.UserPreferences
import com.example.pracav2.data.repository.BaseRepository
import com.example.pracav2.data.responses.TokenResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    context: Context,
    private val tokenApi: TokenRefreshApi
) : Authenticator, BaseRepository(tokenApi) {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)

//        val token =runBlocking {
//            userPreferences.accessToken.first()
//        }
//        return runBlocking {
//            userPreferences.saveAccessTokens(
//                        token!!
//                        ,
//                        token!!
//                    )
//            response.request.newBuilder()
//                        .header("Authorization", "Bearer ${token.toString()}")
//                        .build()

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking { userPreferences.accessToken.first() }
        return runBlocking {
//            when (val tokenResponse = getUpdatedToken()) {
//                is Resource.Failure -> {
//                    userPreferences.saveAccessTokens(
//                        tokenResponse.value.access_token!!,
//                        tokenResponse.value.refresh_token!!
//                    )
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
//                }
//                else -> null
//            }
        }
    }

    private suspend fun getUpdatedToken(): Resource<TokenResponse> {
        val refreshToken = userPreferences.refreshToken.first()
        return safeApiCall { tokenApi.refreshAccessToken(refreshToken) }
    }

}
