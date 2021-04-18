package com.dsoft.liverpooldirectory.data.api.dto.vk.wall

data class PhotoXX(
    val access_key: String,
    val album_id: Int,
    val date: Int,
    val has_tags: Boolean,
    val id: Int,
    val owner_id: Int,
    val post_id: Int,
    val sizes: List<SizeXX>,
    val text: String,
    val user_id: Int
)