package com.sopherwang.mall.feature.authorization

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sopherwang.libraries.network.common.models.Resource
import com.sopherwang.mall.libraries.network.models.SignInRequest
import com.sopherwang.mall.libraries.network.models.SignInResponse
import com.sopherwang.mall.libraries.network.models.SignUpRequest
import com.sopherwang.mall.libraries.network.models.SignUpResponse
import com.sopherwang.mall.libraries.repositories.OnBoardingRepository

class OnBoardingViewModel @ViewModelInject constructor(private val onBoardingRepository: OnBoardingRepository) :
    ViewModel() {
    private val signUpLiveData = MutableLiveData<SignUpRequest>()
    private val loginLiveData = MutableLiveData<SignInRequest>()

    val signUpTokenLiveData: LiveData<Resource<String>> = signUpLiveData.switchMap {
        onBoardingRepository.register(it)
    }

    val loginTokenLiveData: LiveData<Resource<String>> = loginLiveData.switchMap {
        onBoardingRepository.login(it)
    }

    fun updateSignUpRequest(phoneNumber: String,
                            password: String,
                            authCode: String) {
        signUpLiveData.value = SignUpRequest(phoneNumber, password, authCode)
    }

    fun updateLoginRequest(phoneNumber: String,
                           password: String) {
        loginLiveData.value = SignInRequest(phoneNumber, password)
    }
}
