package com.sopherwang.mall.libraries.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.sopherwang.mall.libraries.network.ApiStores
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(private val apiStores: ApiStores) {

    fun register(phoneNumber: String,
                 password: String,
                 authCode: String): MutableLiveData<String> {
        val data = MutableLiveData<String>()

        apiStores.register(phoneNumber, password, authCode)
            .flatMap {
                if (it.code == 200) {
                    apiStores.login(phoneNumber, password)
                } else {
                    Log.d("jiajun", "sign up failed ${it.message}")
                    Observable.never()
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code == 200) {
                    Log.d("jiajun", "" + it.data)
                    data.value = it.data?.token
                }
            }

        return data
    }

    fun login(phoneNumber: String,
              password: String): MutableLiveData<String> {
        val data = MutableLiveData<String>()

        apiStores.login(phoneNumber, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code == 200) {
                    Log.d("jiajun", "" + it.data)
                    data.value = it.data?.token
                }
            }

        return data
    }
}
