package com.sopherwang.mall.libraries.network

import com.sopherwang.mall.libraries.network.models.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiStores {
    @GET("home/content")
    abstract fun getHomeContent(): Observable<HomeContentResponse>

    @POST("/register")
    abstract fun register(signUpRequest: SignUpRequest): Observable<SignUpResponse>

    @POST("/login")
    abstract fun login(signInRequest: SignInRequest): Observable<SignInResponse>
}
