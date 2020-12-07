package com.example.liverpooldirectory.vk

import retrofit2.http.GET

interface VKAPIRequest {
    @GET("wall.get?owner_id=-23328324&count=20&filter=owner&access_token=3428aceb4b8d5f4b5f9eb763285a2e2229f82c7daebeeda871fdb495f9147b43900960d6b7124a2a811da&v=5.126")
    suspend fun getWall(): VKApiJSON
}

