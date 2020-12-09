package com.example.liverpooldirectory.vk

import retrofit2.http.GET
import retrofit2.http.Query

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&count=30&filter=owner&v=5.126")
    suspend fun getWall(@Query("access_token") token: String): VKApiJSON
}