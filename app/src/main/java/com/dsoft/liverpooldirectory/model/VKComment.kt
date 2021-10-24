package com.dsoft.liverpooldirectory.model

data class VKComment(
    var userId: Long,
    var commentUserId: Long,
    var text: String,
    var date: Long,
    var firstName: String,
    var lastName: String,
    var profilePic: String
)