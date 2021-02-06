package com.sopherwang.mall.libraries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.sopherwang.libraries.network.common.AppExecutors
import com.sopherwang.libraries.network.common.NetworkBoundResource
import com.sopherwang.libraries.network.common.models.*
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.models.AddCartItemRequest
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers

class CartRepository(
    private val appExecutors: AppExecutors,
    private val apiStores: ApiStores
) {
    private val addCartItemData = MutableLiveData<Int>().apply {
        postValue(null)
    }

    fun addCartItem(addCartItemRequest: AddCartItemRequest): LiveData<Resource<Int>> {
        return object : NetworkBoundResource<Int, Int>(appExecutors) {
            override fun saveCallResult(item: Int) {
                appExecutors.mainThread().execute {
                    addCartItemData.value = item
                }
            }

            override fun shouldFetch(data: Int?) = true

            override fun loadFromDb() = addCartItemData

            override fun createCall(): LiveData<ApiResponse<Int>> {
                return LiveDataReactiveStreams.fromPublisher(apiStores.addCartItem(addCartItemRequest)
                    .map {
                        when {
                            it.code == 200  -> {
                                if (it.data == null) {
                                    return@map ApiEmptyResponse<Int>()
                                } else {
                                    return@map ApiSuccessResponse(it.data!!)
                                }
                            }
                            else -> {
                                return@map ApiErrorResponse<Int>(
                                    it.message ?: "Unknown Error"
                                )
                            }
                        }
                    }
                    .onErrorReturn {
                        ApiResponse.create(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .toFlowable(BackpressureStrategy.BUFFER))
            }
        }.asLiveData()
    }
}