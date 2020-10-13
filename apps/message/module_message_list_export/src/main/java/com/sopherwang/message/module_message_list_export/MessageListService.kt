package com.sopherwang.message.module_message_list_export

import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.sopherwang.message.library_common_network.models.Message
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@Route(path = MessageListRouter.PATH_SERVICE_MESSAGE_LIST)
class MessageListService : IMessageListService{
    private val messageRelay: PublishSubject<Message> = PublishSubject.create()

    fun addMessage(message: Message) {
        Log.d("MessageListService", "addMessage")
        messageRelay.onNext(message)
    }

    override fun onListItemClicked(): Observable<Message> {
        return messageRelay
    }
}
