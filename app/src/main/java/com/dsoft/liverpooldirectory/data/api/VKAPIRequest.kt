package com.dsoft.liverpooldirectory.data.api

import com.dsoft.liverpooldirectory.data.api.dto.vk.comments.VKComment
import com.dsoft.liverpooldirectory.data.api.dto.vk.wall.VKWall
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&filter=owner&v=5.84")
    suspend fun getWall(
        @Query("access_token") token: String,
        @Query("count") count: Int
    ): VKWall

    @GET("wall.getComments?owner_id=-23328324&v=5.126")
    suspend fun getComments(
        @Query("post_id") postId: String,
        @Query("access_token") token: String
    ): VKComment

    @POST("wall.createComment?owner_id=-23328324&v=5.126")
    suspend fun postComment(
        @Query("post_id") postId: String,
        @Query("access_token") token: String,
        @Query("message") message: String
    )
}