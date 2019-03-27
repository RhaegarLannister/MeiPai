package com.rhaegar.meipai

import android.app.Activity
import android.app.Application
import com.rhaegar.meipai.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class App: Application(),HasActivityInjector{

    companion object {
        lateinit var app: App
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        app=this
        super.onCreate()
        AppInjector.init(this)
    }
    override fun activityInjector() = dispatchingAndroidInjector

}