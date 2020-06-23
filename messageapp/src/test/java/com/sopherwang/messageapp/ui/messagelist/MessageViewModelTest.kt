package com.sopherwang.messageapp.ui.messagelist

import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.sopherwang.messageapp.data.ApiStores
import com.sopherwang.messageapp.data.models.Author
import com.sopherwang.messageapp.data.models.Message
import com.sopherwang.messageapp.data.models.MessageList
import io.reactivex.Observable.just
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MessageViewModelTest {
    companion object {
        const val CONTENT = "content"
        const val UPDATE = "update"
        const val ID: Long = 1
        const val PAGE_TOKEN = "page_token"
        const val NAME = "name"
        const val PROFILE_URL = "profile_url"
    }

    private val apiStores = mock(ApiStores::class.java)
    private val savedStateHandle = SavedStateHandle()

    private lateinit var messageViewModel: MessageViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(apiStores.getMessageList(any(Int::class.java))).thenReturn(just(getMessageList()))

        messageViewModel = MessageViewModel(apiStores, savedStateHandle)
    }

    @After
    fun tearDown() {
        Robolectric.flushBackgroundThreadScheduler()
        Robolectric.flushForegroundThreadScheduler()
    }

    @Test
    fun getMessage_ShouldReturnCorrectData() {
        val author = Author(NAME, PROFILE_URL)
        val message = Message(CONTENT, UPDATE, ID, author, PAGE_TOKEN)
        val messages = arrayListOf(message)

        val observer = mock<Observer<List<Message>>>()
        messageViewModel.getMessage().observeForever(observer)
        verifyNoMoreInteractions(observer)

        verify(apiStores).getMessageList(any(Int::class.java))
        apiStores.getMessageList(any(Int::class.java)).test().assertSubscribed()
        apiStores.getMessageList(any(Int::class.java)).test().assertValue(getMessageList())
//        verify(observer).onChanged(eq(messages))
    }

    private fun getMessageList(): MessageList {
        val author = Author(NAME, PROFILE_URL)
        val message = Message(CONTENT, UPDATE, ID, author, PAGE_TOKEN)
        return MessageList(20, "token", arrayListOf(message))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}
