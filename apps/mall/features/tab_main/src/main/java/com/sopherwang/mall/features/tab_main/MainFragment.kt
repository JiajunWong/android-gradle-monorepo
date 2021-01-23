package com.sopherwang.mall.features.tab_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopherwang.mall.libraries.network.ApiStores
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var apiStores: ApiStores

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiStores.getHomeContent()
            .subscribeOn(io())
            .observeOn(mainThread())
            .subscribe {
                if (it.code == 200) {
                    Log.d("Jiajun", "Success: $it.data")
                } else {
                    Log.d("Jiajun", "Failed")
                }
            }
    }
}
