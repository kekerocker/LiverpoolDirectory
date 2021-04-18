package com.dsoft.liverpooldirectory.data.api.dto.vk.wall

data class Link(
    val caption: String,
    val description: String,
    val is_favorite: Boolean,
    val photo: PhotoX,
    val title: String,
    val url: String
)