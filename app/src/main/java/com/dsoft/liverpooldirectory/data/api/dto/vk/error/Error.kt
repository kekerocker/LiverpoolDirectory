package com.dsoft.liverpooldirectory.data.api.dto.vk.error

data class Error(
    val error_code: Int?,
    val error_msg: String,
    val request_params: List<RequestParam>
)