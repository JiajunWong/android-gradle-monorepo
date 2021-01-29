package com.sopherwang.mall.feature.authorization

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sopherwang.mall.libraries.repositories.OnBoardingRepository

class OnBoardingViewModel @ViewModelInject constructor(private val homeContentRepository: OnBoardingRepository) :
    ViewModel() {
}