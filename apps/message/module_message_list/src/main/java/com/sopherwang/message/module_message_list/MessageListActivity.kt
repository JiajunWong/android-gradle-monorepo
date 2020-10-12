package com.sopherwang.message.module_message_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.launcher.ARouter
import com.sopherwang.message.module_message_list_export.MessageListRouter
import com.sopherwang.message.module_message_list_export.MessageListUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageListActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, MessageListUtil.getMessageListFragment())
        fragmentTransaction.commit()
    }
}
