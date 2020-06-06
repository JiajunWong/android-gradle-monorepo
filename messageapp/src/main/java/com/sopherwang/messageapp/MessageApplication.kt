package com.sopherwang.messageapp

import android.app.Application
import com.sopherwang.dagger_integration.HasComponent
import com.sopherwang.messageapp.di.ApplicationComponent
import com.sopherwang.messageapp.di.DaggerApplicationComponent

class MessageApplication : Application(), HasComponent<Any> {
    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
            .application(this)
            .build()
        component.inject(this)
    }

    override fun component(): Any {
        return component
    }

}