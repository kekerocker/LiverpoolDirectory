package com.dsoft.liverpooldirectory2.data.dto.wall

data class Poll(
    val answer_ids: List<Any>,
    val answers: List<Answer>,
    val author_id: Int,
    val background: Background,
    val can_edit: Boolean,
    val can_report: Boolean,
    val can_share: Boolean,
    val can_vote: Boolean,
    val closed: Boolean,
    val created: Int,
    val disable_unvote: Boolean,
    val end_date: Int,
    val id: Int,
    val is_board: Boolean,
    val multiple: Boolean,
    val owner_id: Int,
    val question: String,
    val votes: Int
)