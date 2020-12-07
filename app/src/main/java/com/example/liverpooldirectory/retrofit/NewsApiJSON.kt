package com.example.liverpooldirectory.retrofit

data class NewsApiJSON(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)