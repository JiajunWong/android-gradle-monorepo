package com.sopherwang.messageapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MessageApplication : Application(){

    override fun onCreate() {
        super.onCreate()
    }
}