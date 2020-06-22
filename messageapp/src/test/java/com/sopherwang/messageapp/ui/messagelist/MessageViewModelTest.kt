package com.sopherwang.messageapp.ui.messagelist

import com.sopherwang.message_demo.features.message_list.MessageViewModel
import com.sopherwang.messageapp.data.ApiStores
import com.sopherwang.messageapp.data.models.Message
import com.sopherwang.messageapp.data.models.MessageList
import io.reactivex.Observable.just
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class MessageViewModelTest {

    private val apiStores = mock(ApiStores::class.java)
    private val messageViewModel = MessageViewModel(apiStores)

    @Before
    fun setup() {
        `when`(apiStores.getMessageList(any())).thenReturn(just(getMessageList()))
    }

    private fun getMessageList(): MessageList {
        return MessageList(20, "token", arrayListOf())
    }
}
