package com.sopherwang.message.app_root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopherwang.message.module_message_list_export.MessageListUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MessageListUtil.setMessageListFragment(supportFragmentManager.beginTransaction(), R.id.fragment_container)
    }
}
