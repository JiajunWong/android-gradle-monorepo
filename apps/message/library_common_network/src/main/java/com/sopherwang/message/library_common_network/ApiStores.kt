package com.sopherwang.message.library_common_network

import com.sopherwang.message.library_common_network.models.MessageList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiStores {
    @GET("messages")
    abstract fun getMessageList(@Query("limit") limit: Int): Observable<MessageList>
}
