package com.sopherwang.messageapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sopherwang.message_demo.features.message_list.MessageViewModel
import com.sopherwang.messageapp.viewmodel.MessageViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun messageViewModel(messageViewModel: MessageViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MessageViewModelFactory): ViewModelProvider.Factory
}
