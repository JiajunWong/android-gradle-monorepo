package com.sopherwang.mall.libraries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.sopherwang.libraries.network.common.AppExecutors
import com.sopherwang.libraries.network.common.NetworkBoundResource
import com.sopherwang.libraries.network.common.models.*
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.models.HomeContentData
import io.reactivex.BackpressureStrategy
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

class HomeContentRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiStores: ApiStores
) {
    private val data = MutableLiveData<HomeContentData>().apply { postValue(null) }

    fun getHomeContent(): LiveData<Resource<HomeContentData>> {
        return object : NetworkBoundResource<HomeContentData, HomeContentData>(appExecutors) {
            override fun saveCallResult(item: HomeContentData) {
                appExecutors.mainThread().execute {
                    data.value = item
                }
            }

            override fun shouldFetch(data: HomeContentData?): Boolean = true

            override fun loadFromDb(): LiveData<HomeContentData> = data

            override fun createCall(): LiveData<ApiResponse<HomeContentData>> {
                return LiveDataReactiveStreams.fromPublisher(apiStores.getHomeContent()
                    .map {
                        when {
                            it.code >= 200 && it.code < 300 -> {
                                if (it.code == 204 || it.data == null) {
                                    return@map ApiEmptyResponse<HomeContentData>()
                                } else {
                                    return@map ApiSuccessResponse(it.data!!)
                                }
                            }
                            else -> {
                                return@map ApiErrorResponse<HomeContentData>(
                                    it.message ?: "Unknown Error"
                                )
                            }
                        }
                    }
                    .onErrorReturn {
                        ApiResponse.create<HomeContentData>(it)
                    }
                    .subscribeOn(io())
                    .toFlowable(BackpressureStrategy.BUFFER)
                )
            }
        }.asLiveData()
    }
}
