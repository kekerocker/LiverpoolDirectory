package com.example.liverpooldirectory.socialapi

import retrofit2.http.GET
import retrofit2.http.Query

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&count=9&filter=owner&v=5.126")
    suspend fun getWall(@Query("access_token") token: String): VKApiJSON
}