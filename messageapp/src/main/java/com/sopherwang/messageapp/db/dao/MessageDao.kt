package com.sopherwang.messageapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopherwang.messageapp.AppConfig
import com.sopherwang.messageapp.data.models.Message

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMessages(messageEntities: List<Message>)

//    @Query("SELECT * FROM " + AppConfig.DB_NAME + " ORDER BY id ASC")
//    fun loadMessages(): DataSource.Factory<Int?, ModelMessage?>?

    @Query("DELETE FROM " + AppConfig.DB_NAME + " WHERE id = :id")
    fun deleteMessage(id: Long)

    @Query("SELECT page_token FROM " + AppConfig.DB_NAME + " ORDER BY id DESC LIMIT 1")
    fun getNewestToken(): String?
}