package com.sopherwang.mall.libraries.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import com.sopherwang.libraries.network.common.AppExecutors
import com.sopherwang.libraries.network.common.NetworkBoundResource
import com.sopherwang.libraries.network.common.models.*
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.SessionManager
import com.sopherwang.mall.libraries.network.models.SignInRequest
import com.sopherwang.mall.libraries.network.models.SignUpRequest
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiStores: ApiStores,
    private val sessionManager: SessionManager
) {
    val signUpLiveData = MutableLiveData<String>().apply { postValue(null) }
    val loginLiveData = MutableLiveData<String>().apply { postValue(null) }

    fun register(signUpRequest: SignUpRequest): LiveData<Resource<String>> {
        return object : NetworkBoundResource<String, String>(appExecutors) {
            override fun saveCallResult(item: String) {
                appExecutors.mainThread().execute {
                    signUpLiveData.value = item
                    sessionManager.saveAuthToken(item)
                }
            }

            override fun shouldFetch(data: String?): Boolean = true

            override fun loadFromDb(): LiveData<String> = signUpLiveData

            override fun createCall(): LiveData<ApiResponse<String>> {
                Timber.tag(javaClass.simpleName).d("Start sign up request.")
                return LiveDataReactiveStreams.fromPublisher(
                    apiStores.register(
                        signUpRequest.phoneNumber,
                        signUpRequest.password,
                        signUpRequest.authCode
                    )
                        .flatMap {
                            when (it.code) {
                                200 -> {
                                    return@flatMap apiStores.login(
                                        signUpRequest.phoneNumber,
                                        signUpRequest.password
                                    )
                                }
                                else -> {
                                    throw Exception(it.message ?: "Unknown Error")
                                }
                            }
                        }
                        .map {
                            when (it.code) {
                                200 -> {
                                    if (it.data == null) {
                                        return@map ApiErrorResponse<String>("The token in the response is null.")
                                    } else {
                                        return@map ApiSuccessResponse(it.data!!.token)
                                    }
                                }
                                else -> {
                                    return@map ApiErrorResponse<String>(
                                        it.message ?: "Unknown error"
                                    )
                                }
                            }
                        }
                        .onErrorReturn {
                            ApiResponse.create(it)
                        }
                        .toFlowable(BackpressureStrategy.BUFFER)
                )
            }
        }.asLiveData()
    }

    fun login(signInRequest: SignInRequest): LiveData<Resource<String>> {
        return object : NetworkBoundResource<String, String>(appExecutors) {
            override fun saveCallResult(item: String) {
                appExecutors.mainThread().execute {
                    loginLiveData.value = item
                    sessionManager.saveAuthToken(item)
                }
            }

            override fun shouldFetch(data: String?): Boolean = true

            override fun loadFromDb(): LiveData<String> = loginLiveData

            override fun createCall(): LiveData<ApiResponse<String>> {
                return LiveDataReactiveStreams.fromPublisher(
                    apiStores.login(signInRequest.phoneNumber, signInRequest.password)
                        .map {
                            when (it.code) {
                                200 -> {
                                    if (it.data == null) {
                                        return@map ApiErrorResponse<String>("The token in the response is null.")
                                    } else {
                                        return@map ApiSuccessResponse(it.data!!.token)
                                    }
                                }
                                else -> {
                                    return@map ApiErrorResponse<String>(
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
