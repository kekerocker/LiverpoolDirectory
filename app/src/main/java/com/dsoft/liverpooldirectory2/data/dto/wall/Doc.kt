package com.dsoft.liverpooldirectory2.data.dto.wall

data class Doc(
    val access_key: String,
    val date: Int,
    val ext: String,
    val id: Int,
    val owner_id: Int,
    val preview: Preview,
    val size: Int,
    val title: String,
    val type: Int,
    val url: String
)