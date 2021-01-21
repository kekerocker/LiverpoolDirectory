package com.example.liverpooldirectory.internet.newsapi

data class NewsApiJSON(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)