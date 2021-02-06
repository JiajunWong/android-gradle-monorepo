package com.sopherwang.mall.libraries.network.models

data class AddCartItemRequest (
    val productId: Int,
    val quantity: Int,
    val productAttr: String?)