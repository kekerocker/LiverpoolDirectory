package com.dsoft.liverpooldirectory2.model

data class VKCommentData(
    var userId: Long,
    var commentUserId: Long,
    var text: String,
    var date: Long,
    var firstName: String,
    var lastName: String,
    var profilePic: String
)