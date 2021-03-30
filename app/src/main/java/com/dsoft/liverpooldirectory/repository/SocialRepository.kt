package com.dsoft.liverpooldirectory.repository

import android.content.Context
import com.dsoft.liverpooldirectory.data.api.VKAPIRequest
import com.dsoft.liverpooldirectory.model.vk.comments.VKComments
import com.dsoft.liverpooldirectory.model.vk.wall.VKApiJSON
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
    private val vkApi: VKAPIRequest,
    @ApplicationContext context: Context
) {

    val appPreferences by lazy { AppPreferences(context) }

    suspend fun fetchWallFromPublic(): VKApiJSON {
        return vkApi.getWall(appPreferences.getToken()!!)
    }

    suspend fun getComments(postId: String): VKComments {
        return vkApi.getComments(postId, appPreferences.getToken()!!)
    }

    suspend fun postComment(postId: String, message: String) {
        vkApi.postComment(postId, appPreferences.getToken()!!, message)
    }
}
