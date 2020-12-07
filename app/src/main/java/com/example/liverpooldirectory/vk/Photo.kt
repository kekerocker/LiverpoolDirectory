package com.example.liverpooldirectory.vk

data class Photo(
    val access_key: String,
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val lat: Double,
    val long: Double,
    val owner_id: Int,
    val post_id: Int,
    val sizes: List<Size>,
    val text: String,
    val user_id: Int
)