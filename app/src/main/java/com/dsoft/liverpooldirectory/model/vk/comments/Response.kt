package com.dsoft.liverpooldirectory.model.vk.comments

data class Response(
    val can_post: Boolean,
    val count: Int,
    val current_level_count: Int,
    val groups_can_post: Boolean,
    val items: List<ItemComments>,
    val show_reply_button: Boolean
)