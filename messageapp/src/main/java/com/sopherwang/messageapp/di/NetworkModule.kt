package com.sopherwang.messageapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sopherwang.messageapp.data.ApiStores
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {
  companion object {
    const val BASE_URL = "https://message-list.appspot.com/"
  }

  @Singleton
  @Provides
  @Named("baseUrl")
  fun baseUrl(): String {
    return BASE_URL
  }

  @Singleton
  @Provides
  fun apiStores(retrofit: Retrofit) : ApiStores {
    return retrofit.create(ApiStores::class.java)
  }

  @Singleton
  @Provides
  fun retrofit(@Named("baseUrl") baseUrl: String): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }
}
