package com.dsoft.liverpooldirectory.model.vk.wall

import com.dsoft.liverpooldirectory.model.vk.error.Error

data class VKApiJSON(
    val response: Response?,
    val error: Error?
)