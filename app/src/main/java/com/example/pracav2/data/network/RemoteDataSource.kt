package com.example.pracav2.data.network

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.pracav2.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/api/"
    }
    fun <Api> buildApi(
        api: Class<Api>,
        context: Context
    ): Api {
        val authenticator = TokenAuthenticator(context, buildTokenApi())
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient(authenticator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

    private fun buildTokenApi(): TokenRefreshApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TokenRefreshApi::class.java)
    }

    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                authenticator?.let { client.authenticator(it) }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}
//    fun <Api> buildApi(
//        api: Class<Api>,
//        context: Context
//    ): Api {
//
//
//        //retrofit client
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(
//                OkHttpClient.Builder()
//                    .addInterceptor { chain ->
//                        chain.proceed(chain.request().newBuilder().also {
//                            it.addHeader("Authorization", "Bearer ${token}")
//                        }.build())
//                    }
//                    .also { client ->
//                        if (BuildConfig.DEBUG) {
//                            val logging = HttpLoggingInterceptor()
//                            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//                            client.addInterceptor(logging)
//                        }
//                    }.build()
//            )
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(api)
//    }
//}


