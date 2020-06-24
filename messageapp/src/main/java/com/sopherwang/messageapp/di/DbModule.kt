package com.sopherwang.messageapp.di

import android.content.Context
import androidx.room.Room
import com.sopherwang.messageapp.AppConfig
import com.sopherwang.messageapp.db.MessageDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {

    @Provides
    fun messageDb(@ApplicationContext context: Context): MessageDb {
        return Room.databaseBuilder(context, MessageDb::class.java, AppConfig.DB_NAME).build()
    }
}
