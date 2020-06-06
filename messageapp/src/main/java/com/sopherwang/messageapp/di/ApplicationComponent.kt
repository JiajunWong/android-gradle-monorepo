package com.sopherwang.messageapp.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.sopherwang.messageapp.MainActivity
import com.sopherwang.messageapp.MessageApplication
import com.sopherwang.messageapp.data.ApiStores
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent : MainActivity.ParentComponent {

    override fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    /*
     * This is our custom Application class
     * */
    fun inject(messageApplication: MessageApplication)
}