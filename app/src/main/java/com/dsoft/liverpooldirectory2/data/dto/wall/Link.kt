package com.dsoft.liverpooldirectory2.data.dto.wall

data class Link(
    val caption: String,
    val description: String,
    val is_favorite: Boolean,
    val photo: PhotoX,
    val title: String,
    val url: String
)