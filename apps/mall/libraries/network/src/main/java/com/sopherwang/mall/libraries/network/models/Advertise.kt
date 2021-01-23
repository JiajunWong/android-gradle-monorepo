package com.sopherwang.mall.libraries.network.models

data class Advertise(var id: Int,
                     var name: String,
                     var pic: String,
                     var startTime: String,
                     var endTime: String,
                     var status: Int,
                     var clickCount: Int,
                     var orderCount: Int,
                     var url: String,
                     var note: String,
                     var sort: Int)
