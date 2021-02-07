package com.sopherwang.mall.libraries.data_layer.session

import android.text.TextUtils
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopherwang.mall.libraries.network.SessionManager

class SessionViewModel @ViewModelInject constructor(private val sessionManager: SessionManager): ViewModel() {

    private val sessionLiveData = MutableLiveData<Boolean>().apply {
        value = !TextUtils.isEmpty(sessionManager.fetchAuthToken())
    }

    val hasAuthenticatedLiveData: LiveData<Boolean> get() = sessionLiveData

    fun updateAuthenticationStatus(hasAuthed: Boolean) {
        sessionLiveData.value = hasAuthed
    }

    fun logOut() {
        sessionManager.clearAuthToken()
        sessionLiveData.value = null
    }
}

