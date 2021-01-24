package com.sopherwang.mall.libraries.network.models

data class Subject(
    var id: Int, var categoryId: Int, var title: String,
    var pic: String, var recommendStatus: Int, var createTime: String,
    var collectCount: Int, var readCount: Int, var commentCount: Int,
    var description: String, var showStatus: Int, var categoryName: String
)
