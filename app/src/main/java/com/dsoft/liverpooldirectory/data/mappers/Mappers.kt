package com.dsoft.liverpooldirectory.data.mappers

import com.dsoft.liverpooldirectory.data.dto.comments.VKCommentResponse
import com.dsoft.liverpooldirectory.data.dto.wall.VKApiJSON
import com.dsoft.liverpooldirectory.model.VKCommentData
import com.dsoft.liverpooldirectory.model.VKWallData

fun VKApiJSON.toModel(): List<VKWallData> {
    val list = mutableListOf<VKWallData>()
    if (error != null) {
        list.add(
            VKWallData(
                0,
                "",
                0,
                "",
                0,
                0,
                0,
                0,
                0,
                errorCode = error.error_code ?: 0,
                attachment = VKWallData.Attachment(
                    mapAttachmentType(""),
                    VKWallData.Attachment.Content(
                        "",
                        "",
                        "",
                        "",
                        VKWallData.Attachment.Content.Image(
                            0,
                            0,
                            0,
                            0,
                            false,
                            VKWallData.Attachment.Content.Image.Sizes(0, "", "", 0),
                            "",
                            0
                        )
                    )
                )
            )
        )
        return list.toList()
    } else {
        return response?.items!!.map { item ->
            val image =
                item.attachments?.firstOrNull()?.photo?.sizes?.firstOrNull { size -> size.type == "r" }
            val attachments = item.attachments?.firstOrNull()
            val content = attachments?.link
            val images = content?.photo
            val sizes = images?.sizes?.firstOrNull()
            VKWallData(
                text = item.text ?: "",
                date = item.date ?: 0,
                image = image?.url ?: "",
                likesCount = item.likes?.count ?: 0,
                commentsCount = item.comments?.count ?: 0,
                viewCount = item.views?.count ?: 0,
                postId = item.id ?: 0,
                imageHeight = image?.height ?: 0,
                imageWidth = image?.width ?: 0,
                errorCode = 0,
                attachment = VKWallData.Attachment(
                    mapAttachmentType(attachments?.type ?: ""),
                    VKWallData.Attachment.Content(
                        content?.url ?: "",
                        content?.title ?: "",
                        content?.caption ?: "",
                        content?.description ?: "",
                        VKWallData.Attachment.Content.Image(
                            images?.album_id ?: 0,
                            images?.date ?: 0,
                            images?.id ?: 0,
                            images?.owner_id ?: 0,
                            images?.has_tags ?: false,
                            VKWallData.Attachment.Content.Image.Sizes(
                                sizes?.height ?: 0,
                                sizes?.url ?: "",
                                sizes?.type ?: "",
                                sizes?.width ?: 0
                            ),
                            images?.text ?: "",
                            images?.user_id ?: 0
                        )
                    )
                )
            )
        }
    }
}

fun mapAttachmentType(type: String): VKWallData.Attachment.AttachmentType {
    val attachmentType = when (type) {
        "photo" -> VKWallData.Attachment.AttachmentType.PHOTO
        "video" -> VKWallData.Attachment.AttachmentType.VIDEO
        "audio" -> VKWallData.Attachment.AttachmentType.AUDIO
        "doc" -> VKWallData.Attachment.AttachmentType.DOC
        "wall" -> VKWallData.Attachment.AttachmentType.WALL
        "wall_reply" -> VKWallData.Attachment.AttachmentType.WALL_REPLY
        "sticker" -> VKWallData.Attachment.AttachmentType.STICKER
        "link" -> VKWallData.Attachment.AttachmentType.LINK
        "gift" -> VKWallData.Attachment.AttachmentType.GIFT
        "market" -> VKWallData.Attachment.AttachmentType.MARKET
        "market_album" -> VKWallData.Attachment.AttachmentType.MARKET_ALBUM
        else -> VKWallData.Attachment.AttachmentType.UNKNOWN
    }
    return attachmentType
}

fun VKCommentResponse.toModel(): List<VKCommentData> {
    val list = mutableListOf<VKCommentData>()
    for (x in response.items.indices) {
        val currentItem = response.items[x]
        if (response.profiles.size - 1 >= x) {
            val currentProfile = response.profiles[x]
            list.add(
                VKCommentData(
                    userId = currentProfile.id ?: 0,
                    commentUserId = currentItem.from_id ?: 0,
                    text = currentItem.text ?: "",
                    date = currentItem.date ?: 0,
                    firstName = currentProfile.first_name ?: "",
                    lastName = currentProfile.last_name ?: "",
                    profilePic = currentProfile.photo_100 ?: ""
                )
            )
        } else {
            list.add(VKCommentData(
                userId = 0,
                commentUserId = currentItem.from_id ?: 0,
                text = currentItem.text ?: "",
                date = currentItem.date ?: 0,
                firstName = "Unknown",
                lastName = "User",
                profilePic = ""
            ))
        }
    }

    return list
}