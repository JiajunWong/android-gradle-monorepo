package com.sopherwang.mall.libraries.repositories

import android.util.Log
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.models.SignInRequest
import com.sopherwang.mall.libraries.network.models.SignUpRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(private val apiStores: ApiStores) {

    fun register(signUpRequest: SignUpRequest) {
        apiStores.register(signUpRequest)
            .filter { it.code == 200 }
            .flatMap {
                apiStores.login(
                    SignInRequest(
                        signUpRequest.phoneNumber,
                        signUpRequest.password
                    )
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code == 200) {
                    Log.d("jiajun", "" + it.data)
                }
            }
    }

    fun login(signInRequest: SignInRequest) {
        apiStores.login(signInRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code == 200) {
                    Log.d("jiajun", "" + it.data)
                }
            }
    }
}
