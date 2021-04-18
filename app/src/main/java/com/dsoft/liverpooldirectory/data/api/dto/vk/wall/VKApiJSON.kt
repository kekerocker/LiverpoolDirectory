package com.dsoft.liverpooldirectory.data.api.dto.vk.wall

import com.dsoft.liverpooldirectory.data.api.dto.vk.error.Error

data class VKApiJSON(
    val response: Response?,
    val error: Error?
)