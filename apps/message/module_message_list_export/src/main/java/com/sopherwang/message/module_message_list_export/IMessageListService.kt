package com.sopherwang.message.module_message_list_export

import com.alibaba.android.arouter.facade.template.IProvider
import com.sopherwang.message.library_common_network.models.Message

interface IMessageListService: IProvider {

    fun getMessage(): Message
}
