package com.dsoft.liverpooldirectory.data.api.dto.vk.comments

data class Thread(
    val can_post: Boolean,
    val count: Int,
    val groups_can_post: Boolean,
    val items: List<Any>,
    val show_reply_button: Boolean
)