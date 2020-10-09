package com.sopherwang.messageapp.data.models


data class Message (var content: String, var updated: String, var id: Long, var author: Author, var page_token: String)
