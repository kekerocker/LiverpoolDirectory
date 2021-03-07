package com.dsoft.liverpooldirectory.model.vk.error

data class Error(
    val error_code: Int,
    val error_msg: String,
    val request_params: List<RequestParam>
)