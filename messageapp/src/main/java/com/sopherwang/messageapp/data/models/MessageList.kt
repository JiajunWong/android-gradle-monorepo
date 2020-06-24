package com.sopherwang.messageapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MessageList(@ColumnInfo(name = "count") var count: Int, @ColumnInfo(name = "pageToken") var pageToken: String, @ColumnInfo(name = "messages") var messages: List<Message>)
