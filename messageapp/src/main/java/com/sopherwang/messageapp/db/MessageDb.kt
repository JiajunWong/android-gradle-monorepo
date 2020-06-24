package com.sopherwang.messageapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sopherwang.messageapp.data.models.Message
import com.sopherwang.messageapp.db.dao.MessageDao

@Database(entities = [Message::class], version = 1)
abstract class MessageDb : RoomDatabase(){
    abstract fun messageDao(): MessageDao?
}
