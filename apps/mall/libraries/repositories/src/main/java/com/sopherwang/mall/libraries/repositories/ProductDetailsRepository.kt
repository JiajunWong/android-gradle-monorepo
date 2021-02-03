package com.sopherwang.mall.libraries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.sopherwang.libraries.network.common.AppExecutors
import com.sopherwang.libraries.network.common.NetworkBoundResource
import com.sopherwang.libraries.network.common.models.*
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.models.ProductDetailsData
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers

class ProductDetailsRepository(
    private val appExecutors: AppExecutors,
    private val apiStores: ApiStores
) {
    private val productDetailsData = MutableLiveData<ProductDetailsData>().apply { postValue(null) }

    fun getProductDetails(id: Int): LiveData<Resource<ProductDetailsData>> {
        return object : NetworkBoundResource<ProductDetailsData, ProductDetailsData>(appExecutors) {
            override fun saveCallResult(item: ProductDetailsData) {
                appExecutors.mainThread().execute {
                    productDetailsData.value = item
                }
            }

            override fun shouldFetch(data: ProductDetailsData?) = true

            override fun loadFromDb() = productDetailsData

            override fun createCall(): LiveData<ApiResponse<ProductDetailsData>> {
                return LiveDataReactiveStreams.fromPublisher(apiStores.getProductDetails(id)
                    .map {
                        when {
                            it.code >= 200 && it.code < 300 -> {
                                if (it.code == 204 || it.data == null) {
                                    return@map ApiEmptyResponse<ProductDetailsData>()
                                } else {
                                    return@map ApiSuccessResponse(it.data!!)
                                }
                            }
                            else -> {
                                return@map ApiErrorResponse<ProductDetailsData>(
                                    it.message ?: "Unknown Error"
                                )
                            }
                        }
                    }
                    .onErrorReturn {
                        ApiResponse.create(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .toFlowable(BackpressureStrategy.BUFFER)
                )
            }

        }.asLiveData()
    }
}