package com.dsoft.liverpooldirectory.data.api.dto.vk.comments

data class ItemComments(
    val attachments: List<Attachment>,
    val date: Int,
    val from_id: Int,
    val id: Int,
    val owner_id: Int,
    val parents_stack: List<Any>,
    val post_id: Int,
    val text: String?,
    val thread: Thread
)