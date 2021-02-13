package com.dsoft.liverpooldirectory.internet.socialapi

data class Item(
    val attachments: List<Attachment>?,
    val carousel_offset: Int,
    val comments: Comments,
    val copyright: Copyright,
    val date: Int,
    val donut: Donut,
    val edited: Int,
    val from_id: Int,
    val id: Int,
    val is_favorite: Boolean,
    val is_pinned: Int,
    val likes: Likes,
    val marked_as_ads: Int,
    val owner_id: Int,
    val post_source: PostSource,
    val post_type: String,
    val reposts: Reposts,
    val short_text_rate: Double,
    val signer_id: Int,
    val text: String,
    val views: Views
)