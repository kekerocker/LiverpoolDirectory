package com.dsoft.liverpooldirectory2.data.dto.comments

data class Thread(
    val can_post: Boolean,
    val count: Int,
    val groups_can_post: Boolean,
    val items: List<Any>,
    val show_reply_button: Boolean
)