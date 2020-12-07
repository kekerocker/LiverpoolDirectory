package com.example.liverpooldirectory.model

class VKSaveInfo(val id: Int,
                 val albumId: Int,
                 val ownerId: Int) {
    fun getAttachment() = "photo${ownerId}_$id"
}

class VKFileUploadInfo(val server: String, val photo: String, val hash: String)

class VKServerUploadInfo(val uploadUrl: String, val albumId: Int, val userId: Int)