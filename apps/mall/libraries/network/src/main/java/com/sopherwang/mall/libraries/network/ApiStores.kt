package com.sopherwang.mall.libraries.network

import com.sopherwang.mall.libraries.network.models.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiStores {
    @GET("home/content")
    abstract fun getHomeContent(): Observable<HomeContentResponse>

    @POST("sso/register")
    abstract fun register(@Query("phoneNumber") phoneNumber: String,
                          @Query("password") password: String,
                          @Query("authCode") authCode: String): Observable<SignUpResponse>

    @POST("sso/login")
    abstract fun login(@Query("phoneNumber") phoneNumber: String,
                       @Query("password") password: String): Observable<SignInResponse>

    @GET("product/detail/{id}")
    abstract fun getProductDetails(@Path("id") id: Int): Observable<ProductDetailsResponse>
}
