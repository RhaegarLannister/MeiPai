package com.rhaegar.meipai.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rhaegar.meipai.ui.connect.ConnectViewModel
import com.rhaegar.meipai.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConnectViewModel::class)
    abstract fun bindConnectViewModel(connectViewModel: ConnectViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PaoViewModelFactory): ViewModelProvider.Factory
}