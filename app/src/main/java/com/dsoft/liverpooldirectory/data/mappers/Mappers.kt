package com.dsoft.liverpooldirectory.data.mappers

import com.dsoft.liverpooldirectory.data.api.dto.vk.wall.VKApiJSON
import com.dsoft.liverpooldirectory.model.VKWall

fun VKApiJSON.toModel(): List<VKWall> {
    val list = mutableListOf<VKWall>()
    if (error != null) {
        list.add(
            VKWall(
                0,
                "",
                "",
                0,
                0,
                0,
                0,
                0,
                errorCode = error.error_code ?: 0
            )
        )
    } else {
        for (item in response?.items!!) {
            val image =
                item.attachments?.firstOrNull()?.photo?.sizes?.firstOrNull { size -> size.type == "r" }

            list.add(
                VKWall(
                    text = item.text ?: "",
                    image = image?.url ?: "",
                    likesCount = item.likes?.count ?: 0,
                    commentsCount = item.comments?.count ?: 0,
                    viewCount = item.views?.count ?: 0,
                    postId = item.id ?: 0,
                    imageHeight = image?.height ?: 0,
                    imageWidth = image?.width ?: 0,
                    errorCode = 0
                )
            )
        }
    }

    return list.toList()
}
