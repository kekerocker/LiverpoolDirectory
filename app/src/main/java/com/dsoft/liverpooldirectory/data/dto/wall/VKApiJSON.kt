package com.dsoft.liverpooldirectory.data.dto.wall

import com.dsoft.liverpooldirectory.data.dto.error.Error

data class VKApiJSON(
    val response: Response?,
    val error: Error?
)