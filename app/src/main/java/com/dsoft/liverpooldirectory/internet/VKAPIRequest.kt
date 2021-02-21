package com.dsoft.liverpooldirectory.internet

import com.dsoft.liverpooldirectory.model.vk.comments.VKComments
import com.dsoft.liverpooldirectory.model.vk.wall.VKApiJSON
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&count=50&filter=owner&v=5.126")
    suspend fun getWall(@Query("access_token") token: String): VKApiJSON

    @GET("wall.getComments?owner_id=-23328324&v=5.126")               // post_id = 4945242
    suspend fun getComments(@Query("post_id") postId: String,
                            @Query("access_token") token: String): VKComments

    @POST("wall.createComment?owner_id=-23328324&v=5.126")
    suspend fun postComment(@Query("post_id") postId: String,
                            @Query("access_token") token: String,
                            @Query("message") message: String)
}