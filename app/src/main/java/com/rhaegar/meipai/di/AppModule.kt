package com.rhaegar.meipai.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.rhaegar.meipai.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/18
 */
@Module
class AppModule {


    @Singleton
    @Provides
    fun provideBlueToothAdapter ():BluetoothAdapter{
        val bluetoothManager = App.app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter

    }
}