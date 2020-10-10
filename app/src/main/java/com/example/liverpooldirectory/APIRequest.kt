package com.example.liverpooldirectory

import com.example.liverpooldirectory.api.NewsApiJSON
import retrofit2.http.GET

interface APIRequest {
    @GET("/v1/search?keywords=liverpool&language=en&apiKey=a_iZHBX6B8ppmDusu4YttfxizCA7D5Rw9gC-RZh1_U0Qu98w")
    suspend fun getNews() : NewsApiJSON

}