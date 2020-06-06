package com.sopherwang.message_demo.features.message_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sopherwang.messageapp.data.ApiStores
import com.sopherwang.messageapp.data.models.Message
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageViewModel @Inject constructor(private val apiStore: ApiStores) : ViewModel() {
    private val messageList: MutableLiveData<List<Message>> = MutableLiveData()

    private var disposable: Disposable? = null

    fun getMessage(): LiveData<List<Message>> {
        loadMessage()
        return messageList
    }

    private fun loadMessage() {
        disposable = apiStore.getMessageList(20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { value ->
                    Log.d("Jiajun", "Received: $value")
                    messageList.postValue(value.messages)
                }, // onNext
                { error -> Log.d("Jiajun", "Error: $error") },    // onError
                { Log.d("Jiajun", "Completed!") }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        disposable = null
    }
}