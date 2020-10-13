package com.sopherwang.message.app_root

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.sopherwang.message.module_message_list_export.MessageListService
import com.sopherwang.message.module_message_list_export.MessageListUtil
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var messageListService: MessageListService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARouter.getInstance().inject(this);

        MessageListUtil.setMessageListFragment(
            supportFragmentManager.beginTransaction(),
            R.id.fragment_container
        )

        messageListService.onListItemClicked()
            .subscribeOn(io())
            .observeOn(mainThread())
            .subscribe { Log.d("MainActivity", "content = $it") }
    }
}
