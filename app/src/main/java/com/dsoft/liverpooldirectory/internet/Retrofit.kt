package com.dsoft.liverpooldirectory.internet

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Retrofit @Inject constructor() {

    private val VK_BASE_URL = "https://api.vk.com/method/"

    val api = Retrofit.Builder()
        .baseUrl(VK_BASE_URL)
        .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VKAPIRequest::class.java)
}