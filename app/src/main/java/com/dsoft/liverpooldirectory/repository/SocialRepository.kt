package com.dsoft.liverpooldirectory.repository

import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.data.CommentsDao
import com.dsoft.liverpooldirectory.model.Comments

class SocialRepository (private val commentsDao: CommentsDao) {
    val readComments: LiveData<List<Comments>> = commentsDao.readComments()

    suspend fun addComments(comments: Comments) {
        commentsDao.addComments(comments)
    }

    suspend fun deleteAllComments() {
        commentsDao.deleteAllComments()
    }
}