package com.dsoft.liverpooldirectory2.data.dto.comments

data class Photo(
    val access_key: String,
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val sizes: List<Size>,
    val text: String,
    val user_id: Int
)