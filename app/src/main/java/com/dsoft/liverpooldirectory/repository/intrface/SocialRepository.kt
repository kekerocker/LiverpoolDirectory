package com.dsoft.liverpooldirectory.repository.intrface

import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.model.VKCommentData
import com.dsoft.liverpooldirectory.model.VKWallData

interface SocialRepository {
    suspend fun fetchWallFromPublic(count: Int): List<VKWallData>
    suspend fun getComments(postId: String): List<VKCommentData>
    suspend fun postComment(postId: String, message: String)

    fun getAppPreferences(): AppPreferences
}