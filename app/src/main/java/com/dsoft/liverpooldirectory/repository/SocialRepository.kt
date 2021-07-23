package com.dsoft.liverpooldirectory.repository

import android.content.Context
import com.dsoft.liverpooldirectory.data.api.VKAPIRequest
import com.dsoft.liverpooldirectory.data.mappers.toModel
import com.dsoft.liverpooldirectory.model.VKComment
import com.dsoft.liverpooldirectory.model.VKWall
import com.dsoft.liverpooldirectory.utility.InternetConnection
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
    val vkApi: VKAPIRequest,
    internetConnection: InternetConnection,
    @ApplicationContext context: Context
) {

    val isOnline = internetConnection.isOnline(context)
    val appPreferences by lazy { AppPreferences(context) }

    suspend fun fetchWallFromPublic(count: Int): List<VKWall> {
        return vkApi.getWall(appPreferences.getToken()!!, count).toModel()
    }

    suspend fun getComments(postId: String): List<VKComment> {
        return vkApi.getComments(postId, appPreferences.getToken()!!).toModel()
    }

    suspend fun postComment(postId: String, message: String) {
        vkApi.postComment(postId, appPreferences.getToken()!!, message)
    }
}
