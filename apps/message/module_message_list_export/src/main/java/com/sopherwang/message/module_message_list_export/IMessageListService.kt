package com.sopherwang.message.module_message_list_export

import com.sopherwang.message.library_common_network.models.Message
import io.reactivex.Observable

interface IMessageListService {

    fun onListItemClicked(): Observable<Message>
}
