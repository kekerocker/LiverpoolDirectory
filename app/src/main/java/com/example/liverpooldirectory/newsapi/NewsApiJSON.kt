package com.example.liverpooldirectory.newsapi

data class NewsApiJSON(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)