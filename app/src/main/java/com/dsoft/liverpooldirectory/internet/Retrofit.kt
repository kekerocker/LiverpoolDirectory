package com.dsoft.liverpooldirectory.internet

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Retrofit @Inject constructor() {

    private val VK_BASE_URL = "https://api.vk.com/method/"

    val api = Retrofit.Builder()
        .baseUrl(VK_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(VKAPIRequest::class.java)

}