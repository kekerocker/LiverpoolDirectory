package com.dsoft.liverpooldirectory.model

data class VKWall(
    val postId: Int,
    val text: String,
    val date: Long,
    val image: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val likesCount: Int,
    val commentsCount: Int,
    val viewCount: Int,
    val errorCode: Int,
    val attachment: Attachment
) {
    data class Attachment(
        val type: AttachmentType,
        val link: Content
    ) {
        enum class AttachmentType {
            PHOTO,
            VIDEO,
            AUDIO,
            DOC,
            WALL,
            WALL_REPLY,
            STICKER,
            LINK,
            GIFT,
            // Следующие только для API > 5.52
            MARKET,
            MARKET_ALBUM,
            UNKNOWN
        }

        data class Content(
            val url: String,
            val title: String,
            val caption: String,
            val description: String,
            val photo: Image
        ) {
            data class Image(
                val album_id: Int,
                val date: Int,
                val id: Int,
                val owner_id: Int,
                val has_tags: Boolean,
                val sizes: Sizes,
                val text: String,
                val user_id: Int
            ) {
                data class Sizes(
                    val height: Int,
                    val url: String,
                    val type: String,
                    val width: Int
                )
            }
        }
    }
}