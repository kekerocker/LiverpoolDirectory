package com.dsoft.liverpooldirectory.data.mappers

import com.dsoft.liverpooldirectory.data.api.dto.vk.comments.VKCommentResponse
import com.dsoft.liverpooldirectory.data.api.dto.vk.wall.VKApiJSON
import com.dsoft.liverpooldirectory.model.VKComment
import com.dsoft.liverpooldirectory.model.VKWall

fun VKApiJSON.toModel(): List<VKWall> {
    val list = mutableListOf<VKWall>()
    if (error != null) {
        list.add(
            VKWall(
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
                attachment = VKWall.Attachment(mapAttachmentType(""), VKWall.Attachment.Content("", "", "", "", VKWall.Attachment.Content.Image(0, 0, 0, 0, false, VKWall.Attachment.Content.Image.Sizes(0, "", "", 0), "", 0)))
            )
        )
        return list.toList()
    } else {
        return response?.items!!.map { item ->
            val image = item.attachments?.firstOrNull()?.photo?.sizes?.firstOrNull { size -> size.type == "r" }
            val attachments = item.attachments?.firstOrNull()
            val content = attachments?.link
            val images = content?.photo
            val sizes = images?.sizes?.firstOrNull()
            VKWall(
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
                attachment = VKWall.Attachment(mapAttachmentType(attachments?.type ?: ""), VKWall.Attachment.Content(content?.url ?: "", content?.title ?: "", content?.caption ?: "", content?.description ?: "", VKWall.Attachment.Content.Image(images?.album_id ?: 0, images?.date ?: 0, images?.id ?: 0, images?.owner_id ?: 0, images?.has_tags ?: false, VKWall.Attachment.Content.Image.Sizes(sizes?.height ?: 0, sizes?.url ?: "", sizes?.type ?: "", sizes?.width ?: 0), images?.text ?: "", images?.user_id ?: 0)))
            )
        }
    }
}

fun mapAttachmentType(type: String) : VKWall.Attachment.AttachmentType {
    val attachmentType = when (type) {
        "photo" -> VKWall.Attachment.AttachmentType.PHOTO
        "video" -> VKWall.Attachment.AttachmentType.VIDEO
        "audio" -> VKWall.Attachment.AttachmentType.AUDIO
        "doc" -> VKWall.Attachment.AttachmentType.DOC
        "wall" -> VKWall.Attachment.AttachmentType.WALL
        "wall_reply" -> VKWall.Attachment.AttachmentType.WALL_REPLY
        "sticker" -> VKWall.Attachment.AttachmentType.STICKER
        "link" -> VKWall.Attachment.AttachmentType.LINK
        "gift" -> VKWall.Attachment.AttachmentType.GIFT
        "market" -> VKWall.Attachment.AttachmentType.MARKET
        "market_album" -> VKWall.Attachment.AttachmentType.MARKET_ALBUM
        else -> VKWall.Attachment.AttachmentType.UNKNOWN
    }
    return attachmentType
}

fun VKCommentResponse.toModel(): List<VKComment> {
    return response.items.map { comment ->
        VKComment(
            userId = comment.from_id ?: 0,
            text = comment.text ?: "",
            date = comment.date ?: 0
        )
    }
}
