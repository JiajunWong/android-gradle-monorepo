package com.sopherwang.messageapp.data

import com.sopherwang.messageapp.data.models.MessageList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStores {
    @GET("messages")
    abstract fun getMessageList(@Query("limit") limit: Int): Observable<MessageList>
}
