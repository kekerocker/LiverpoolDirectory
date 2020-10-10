package com.example.liverpooldirectory.api

data class NewsApiJSON(
    val news: List<News>,
    val page: Int,
    val status: String
)