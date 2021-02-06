package com.sopherwang.mall.libraries.repositories

import com.sopherwang.libraries.network.common.AppExecutors
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.SessionManager
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
    fun homeContentRepository(
        appExecutors: AppExecutors,
        apiStores: ApiStores
    ): HomeContentRepository {
        return HomeContentRepository(appExecutors, apiStores)
    }

    @Singleton
    @Provides
    fun onboardingRepository(
        appExecutors: AppExecutors,
        apiStores: ApiStores,
        sessionManager: SessionManager
    ): OnBoardingRepository {
        return OnBoardingRepository(appExecutors, apiStores, sessionManager)
    }

    @Singleton
    @Provides
    fun productDetailsRepository(
        appExecutors: AppExecutors,
        apiStores: ApiStores
    ): ProductDetailsRepository {
        return ProductDetailsRepository(appExecutors, apiStores)
    }

    @Singleton
    @Provides
    fun cartRepository(appExecutors: AppExecutors, apiStores: ApiStores): CartRepository {
        return CartRepository(appExecutors, apiStores)
    }
}
