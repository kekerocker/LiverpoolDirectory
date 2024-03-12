package com.dsoft.liverpooldirectory2.data.api

import com.dsoft.liverpooldirectory2.data.dto.comments.VKCommentResponse
import com.dsoft.liverpooldirectory2.data.dto.wall.VKApiJSON
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&filter=owner&v=5.84")
    suspend fun getWall(
        @Query("access_token") token: String,
        @Query("count") count: Int
    ): VKApiJSON

    @GET("wall.getComments?owner_id=-23328324&extended=1&v=5.126")
    suspend fun getComments(
        @Query("post_id") postId: String,
        @Query("access_token") token: String
    ): VKCommentResponse

    @POST("wall.createComment?owner_id=-23328324&v=5.131")
    suspend fun postComment(
        @Query("post_id") postId: String,
        @Query("access_token") token: String,
        @Query("message") message: String
    )
}