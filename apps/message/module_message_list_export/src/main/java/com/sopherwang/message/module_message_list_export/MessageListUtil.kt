package com.sopherwang.message.module_message_list_export

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.launcher.ARouter


object MessageListUtil {

    fun setMessageListFragment(fragmentTransaction: FragmentTransaction, @IdRes container: Int) {
        val fragment = ARouter.getInstance().build(MessageListRouter.PATH_FRAGMENT_MESSAGE_LIST).navigation() as Fragment
        fragmentTransaction.replace(container, fragment)
        fragmentTransaction.commit()
    }

    fun setMessageDetailFragment(content: String) {
        val params = Bundle()
        params.putString("content", content)
        ARouter.getInstance()
            .build("/app_root/mainActivity")
            .with(params)
            .navigation()
    }

    fun getMessageService(): IMessageListService {
        return ARouter.getInstance().navigation(IMessageListService::class.java)
    }
}
