package com.dsoft.liverpooldirectory2.repository

import com.dsoft.liverpooldirectory2.data.AppPreferences
import com.dsoft.liverpooldirectory2.data.api.VKAPIRequest
import com.dsoft.liverpooldirectory2.data.mappers.toModel
import com.dsoft.liverpooldirectory2.model.VKCommentData
import com.dsoft.liverpooldirectory2.model.VKWallData
import com.dsoft.liverpooldirectory2.repository.intrface.SocialRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepositoryImpl @Inject constructor(
    private val vkApi: VKAPIRequest,
    private val appPreferences: AppPreferences
) : SocialRepository {

    override suspend fun fetchWallFromPublic(count: Int): List<VKWallData> {
        return vkApi.getWall(appPreferences.getToken()!!, count).toModel()
    }

    override suspend fun getComments(postId: String): List<VKCommentData> {
        return vkApi.getComments(postId, appPreferences.getToken()!!).toModel()
    }

    override suspend fun postComment(postId: String, message: String) {
        vkApi.postComment(postId, appPreferences.getToken()!!, message)
    }

    override fun getAppPreferences(): AppPreferences = appPreferences

}
