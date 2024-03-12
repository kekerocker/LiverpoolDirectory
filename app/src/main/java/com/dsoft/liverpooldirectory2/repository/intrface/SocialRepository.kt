package com.dsoft.liverpooldirectory2.repository.intrface

import com.dsoft.liverpooldirectory2.data.AppPreferences
import com.dsoft.liverpooldirectory2.model.VKCommentData
import com.dsoft.liverpooldirectory2.model.VKWallData

interface SocialRepository {
    suspend fun fetchWallFromPublic(count: Int): List<VKWallData>
    suspend fun getComments(postId: String): List<VKCommentData>
    suspend fun postComment(postId: String, message: String)

    fun getAppPreferences(): AppPreferences
}