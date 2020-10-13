package com.sopherwang.message.module_message_list_export

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class MessageListModule {
    @Singleton
    @Provides
    fun messageListService() : MessageListService {
        return MessageListService()
    }
}
