package com.sopherwang.mall.libraries.network.models

data class ProductDetailsResponse(val code: Int, val message: String?, val data: ProductDetailsData?)

data class ProductDetailsData(
    val product: Product?,
    val brand: Brand?,
    val productAttributeList: List<ProductAttribute>?
)
