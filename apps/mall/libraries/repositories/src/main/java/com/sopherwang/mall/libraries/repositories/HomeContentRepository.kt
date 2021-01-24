package com.sopherwang.mall.libraries.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sopherwang.mall.libraries.network.ApiStores
import com.sopherwang.mall.libraries.network.models.Advertise
import com.sopherwang.mall.libraries.network.models.HomeContentData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeContentRepository @Inject constructor(private val apiStores: ApiStores) {

    fun getAdvertises(): LiveData<HomeContentData> {
        val data = MutableLiveData<HomeContentData>()

        //TODO: Need to resolve the disposable
        apiStores.getHomeContent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.code == 200) {
                    data.value = it.data
                }
            }

        return data
    }
}
