package com.dsoft.liverpooldirectory2.usecase

import com.dsoft.liverpooldirectory2.data.AppPreferences
import com.dsoft.liverpooldirectory2.model.VKCommentData
import com.dsoft.liverpooldirectory2.model.VKWallData
import com.dsoft.liverpooldirectory2.repository.SocialRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialUseCase @Inject constructor(
    private val repository: SocialRepositoryImpl
) {

    val vkSuccessConnection: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun getAppPreferences(): AppPreferences {
        return repository.getAppPreferences()
    }

    suspend fun fetchWallFromPublic(count: Int): List<VKWallData> {
        return repository.fetchWallFromPublic(count)
    }

    suspend fun postComment(postId: String, message: String) {
        repository.postComment(postId, message)
    }

    suspend fun getComments(postId: String): List<VKCommentData> {
        return repository.getComments(postId)
    }

}