package com.sopherwang.mall.libraries.network.models

data class SignInResponse(var code: Int, var message: String?, var data: SignInResponseData?)

data class SignInResponseData(
    var token: String,
    var tokenHead: String
)
