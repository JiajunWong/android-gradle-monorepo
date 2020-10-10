package com.sopherwang.message.library_common_network.models

import com.sopherwang.message.library_common_network.models.Author


data class Message (var content: String, var updated: String, var id: Long, var author: Author, var page_token: String)
