package com.sopherwang.mall.libraries.network.models

data class HomeContentResponse(var code: Int, var message: String, var data: HomeContentData)

data class HomeContentData(var advertiseList: List<Advertise>)