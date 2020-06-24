package com.sopherwang.messageapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message (@ColumnInfo(name = "content") var content: String,
                    @ColumnInfo(name = "updated") var updated: String,
                    @PrimaryKey @ColumnInfo(name = "id") var id: Long,
                    @ColumnInfo(name = "author") var author: Author,
                    @ColumnInfo(name = "page_token") var page_token: String)
