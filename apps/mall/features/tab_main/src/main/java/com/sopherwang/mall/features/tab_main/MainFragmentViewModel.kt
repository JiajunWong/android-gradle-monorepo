package com.sopherwang.mall.features.tab_main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sopherwang.mall.libraries.network.models.HomeContentData
import com.sopherwang.mall.libraries.repositories.HomeContentRepository

class MainFragmentViewModel @ViewModelInject constructor(private val homeContentRepository: HomeContentRepository) :
    ViewModel() {
    val messageList: LiveData<HomeContentData> = homeContentRepository.getAdvertises()
}
