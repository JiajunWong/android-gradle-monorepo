package com.sopherwang.mall.libraries.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sopherwang.libraries.network.common.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/"
    }

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrl(): String {
        return BASE_URL
    }

    @Singleton
    @Provides
    fun apiStores(retrofit: Retrofit): ApiStores {
        return retrofit.create(ApiStores::class.java)
    }

    @Singleton
    @Provides
    fun retrofit(@Named("baseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun appExecutors(): AppExecutors {
        return AppExecutors()
    }

    @Singleton
    @Provides
    fun okhttpClient(sessionManager: SessionManager): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(sessionManager))
            .build()
    }

    @Singleton
    @Provides
    fun sessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }
}
