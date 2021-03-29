package com.dsoft.liverpooldirectory.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.data.CommentsDao
import com.dsoft.liverpooldirectory.internet.VKAPIRequest
import com.dsoft.liverpooldirectory.model.Comments
import com.dsoft.liverpooldirectory.model.vk.comments.VKComments
import com.dsoft.liverpooldirectory.model.vk.wall.VKApiJSON
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialRepository @Inject constructor(
    private val commentsDao: CommentsDao,
    private val vkApi: VKAPIRequest,
    @ApplicationContext context: Context
) {

    val appPreferences by lazy { AppPreferences(context) }

    val readComments: LiveData<List<Comments>> = commentsDao.readComments()

    suspend fun addComments(comments: Comments) {
        commentsDao.addComments(comments)
    }

    suspend fun deleteAllComments() {
        commentsDao.deleteAllComments()
    }

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
