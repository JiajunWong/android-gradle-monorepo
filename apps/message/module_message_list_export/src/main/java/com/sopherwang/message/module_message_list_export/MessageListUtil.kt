package com.sopherwang.message.module_message_list_export

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

object MessageListUtil {

    fun getMessageListFragment(): Fragment {
        return ARouter.getInstance().build(MessageListRouter.PATH_FRAGMENT_MESSAGE_LIST).navigation() as Fragment
    }

    fun getMessageService(): IMessageListService {
        //return ARouter.getInstance().navigation(ICartService.class);//如果只有一个实现，这种方式也可以
        return ARouter.getInstance().build(MessageListRouter.PATH_SERVICE_MESSAGE_LIST).navigation() as IMessageListService
    }
}
