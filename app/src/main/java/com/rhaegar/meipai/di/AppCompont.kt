package com.rhaegar.meipai.di

import android.app.Application
import com.rhaegar.meipai.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/18
 */
@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, AndroidInjectionModule::class, ActivityModule::class])
interface AppCompont {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppCompont
    }
    fun inject(activity: App)
}