package com.dsoft.liverpooldirectory.model

data class VKWall(
    val postId: Int,
    val text: String,
    val date: Long,
    val image: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val likesCount : Int,
    val commentsCount: Int,
    val viewCount: Int,
    val errorCode: Int
)