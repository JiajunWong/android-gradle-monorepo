package com.sopherwang.messageapp.data.models


data class MessageList(var count: Int, var pageToken: String, var messages: List<Message>)
