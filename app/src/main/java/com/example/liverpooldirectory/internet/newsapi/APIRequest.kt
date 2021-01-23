package com.example.liverpooldirectory.internet.newsapi

import retrofit2.http.GET

interface APIRequest {
    @GET("v2/everything?q=ливерпуль&language=ru&sortBy=publishedAt&apiKey=7c6d166b555a468c8646cd3adeeec69f")
    suspend fun getNews(): NewsApiJSON
}