package com.dsoft.liverpooldirectory.repository

import com.dsoft.liverpooldirectory.data.AppPreferences
import com.dsoft.liverpooldirectory.data.api.VKAPIRequest
import com.dsoft.liverpooldirectory.data.mappers.toModel
import com.dsoft.liverpooldirectory.model.VKCommentData
import com.dsoft.liverpooldirectory.model.VKWallData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
    private val vkApi: VKAPIRequest,
    val appPreferences: AppPreferences
) {
    suspend fun fetchWallFromPublic(count: Int): List<VKWallData> {
        return vkApi.getWall(appPreferences.getToken()!!, count).toModel()
    }

    suspend fun getComments(postId: String): List<VKCommentData> {
        return vkApi.getComments(postId, appPreferences.getToken()!!).toModel()
    }

    suspend fun postComment(postId: String, message: String) {
        vkApi.postComment(postId, appPreferences.getToken()!!, message)
    }
}
