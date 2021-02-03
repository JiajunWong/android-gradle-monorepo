package com.sopherwang.mall.libraries.network.models

data class ProductAttribute(
    val id: Int, val productAttributeCategoryId: Int, val name: String,
    val selectType: Int, val inputType: Int, val inputList: String, val sort: Int,
    val filterType: Int, val searchType: Int, val relatedStatus: Int, val handAddStatus: Int,
    val type: Int
)
