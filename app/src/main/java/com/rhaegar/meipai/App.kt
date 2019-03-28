package com.rhaegar.meipai

import android.app.Activity
import android.app.Application
import android.app.Service
import com.rhaegar.meipai.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class App : Application(), HasActivityInjector, HasServiceInjector {

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceInjector
    }

    companion object {
        lateinit var app: App
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        app = this
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}