package com.example.liverpooldirectory.vk

import retrofit2.http.GET

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&count=20&filter=owner&access_token=54fffc8e761155232813ce39ae05e9b42003ee0b64010a211eae0e3abac2666817b6a77359d10e3c3e358&v=5.126")
    suspend fun getWall(): VKApiJSON
}

