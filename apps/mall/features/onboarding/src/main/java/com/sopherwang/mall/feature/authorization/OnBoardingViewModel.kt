package com.sopherwang.mall.feature.authorization

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.sopherwang.mall.libraries.network.models.SignInRequest
import com.sopherwang.mall.libraries.network.models.SignUpRequest
import com.sopherwang.mall.libraries.repositories.OnBoardingRepository

class OnBoardingViewModel @ViewModelInject constructor(private val onBoardingRepository: OnBoardingRepository) :
    ViewModel() {
    private val signUpLiveData = MutableLiveData<SignUpRequest>()
    private val loginLiveData = MutableLiveData<SignInRequest>()

    val signUpTokenLiveData: LiveData<String> = signUpLiveData.switchMap { input ->
        onBoardingRepository.register(input.phoneNumber, input.password, input.authCode)
    }

    val loginTokenLiveData: LiveData<String> = loginLiveData.switchMap {
        onBoardingRepository.login(it.phoneNumber, it.password)
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
