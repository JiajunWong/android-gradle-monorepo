package com.sopherwang.mall.libraries.repositories

import com.sopherwang.mall.libraries.network.ApiStores
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun homeContentRepository(apiStores: ApiStores): HomeContentRepository {
        return HomeContentRepository(apiStores)
    }
}