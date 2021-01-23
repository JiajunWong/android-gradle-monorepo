package com.sopherwang.mall.libraries.network

import com.sopherwang.mall.libraries.network.models.HomeContentResponse
import io.reactivex.Observable
import retrofit2.http.GET

public interface ApiStores {
    @GET("home/content")
    abstract fun getHomeContent(): Observable<HomeContentResponse>
}