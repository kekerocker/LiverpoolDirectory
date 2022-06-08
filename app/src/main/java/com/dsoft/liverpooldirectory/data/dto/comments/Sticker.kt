package com.dsoft.liverpooldirectory.data.dto.comments

data class Sticker(
    val images: List<Image>,
    val images_with_background: List<ImagesWithBackground>,
    val product_id: Int,
    val sticker_id: Int
)