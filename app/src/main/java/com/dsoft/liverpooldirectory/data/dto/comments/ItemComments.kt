package com.dsoft.liverpooldirectory.data.dto.comments

data class ItemComments(
    val attachments: List<Attachment>?,
    val date: Long?,
    val from_id: Long?,
    val id: Int?,
    val owner_id: Int?,
    val parents_stack: List<Any>?,
    val post_id: Int?,
    val text: String?,
    val thread: Thread?
)