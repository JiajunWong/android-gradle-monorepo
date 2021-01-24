package com.sopherwang.mall.libraries.network.models

data class HomeContentResponse(var code: Int, var message: String?, var data: HomeContentData?)

data class HomeContentData(
    var advertiseList: List<Advertise>?,
    var brandList: List<Brand>?,
    var newProductList: List<Product>?,
    var hotProductList: List<Product>,
    var subjectList: List<Subject>?
)