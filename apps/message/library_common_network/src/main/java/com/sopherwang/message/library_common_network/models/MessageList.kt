package com.sopherwang.message.library_common_network.models

import com.sopherwang.message.library_common_network.models.Message


data class MessageList(var count: Int, var pageToken: String, var messages: List<Message>)
