package com.dsoft.liverpooldirectory.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.dsoft.liverpooldirectory.data.CommentsDao
import com.dsoft.liverpooldirectory.di.DaggerComponent
import com.dsoft.liverpooldirectory.model.Comments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SocialRepository (private val commentsDao: CommentsDao) {
    private var component = DaggerComponent.create()

    val readComments: LiveData<List<Comments>> = commentsDao.readComments()

    suspend fun addComments(comments: Comments) {
        commentsDao.addComments(comments)
    }

    suspend fun deleteAllComments() {
        commentsDao.deleteAllComments()
    }

    private fun fetchWallFromPublic(token: String) {
        val api = component.getRetrofit().api
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getWall(token)
            } catch (e: Exception) {
                Log.e("Social95", e.toString())
            }
        }
    }
}