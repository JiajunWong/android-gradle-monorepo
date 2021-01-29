package com.sopherwang.mall.libraries.network.models

data class SignUpRequest(var phoneNumber: String, var password: String, var authCode: String)