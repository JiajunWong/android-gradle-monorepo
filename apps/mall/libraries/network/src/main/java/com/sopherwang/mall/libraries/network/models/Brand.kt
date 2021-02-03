package com.sopherwang.mall.libraries.network.models

data class Brand(
    var id: Int,
    var name: String?,
    var firstLetter: String?,
    var sort: Int,
    var factoryStatus: Int,
    var showStatus: Int,
    var productCount: Int,
    var productCommentCount: Int,
    var logo: String?,
    var bigPic: String?,
    var brandStory: String?
)
