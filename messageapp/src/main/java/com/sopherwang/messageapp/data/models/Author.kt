package com.sopherwang.messageapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Author (@ColumnInfo(name = "name") var name: String, @ColumnInfo(name = "photoUrl") var photoUrl: String)
