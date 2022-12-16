package com.example.vkalbums.api

import com.example.vkalbums.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.vk.com/method/"
    private const val apiKey = BuildConfig.API_KEY      // Load API key from application settings
    private const val version = BuildConfig.VERSION     // Load VK API version from settings

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val url = chain
                            .request()
                            .url()
                            .newBuilder()
                            .addQueryParameter("v", version)
                            .addQueryParameter("access_token", apiKey)
                            .build()
                        chain.proceed(chain.request().newBuilder().url(url).build())
                    }
                    .build()
            )
            .build()
    }
}