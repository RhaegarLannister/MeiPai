package com.rhaegar.meipai.ui.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rhaegar.meipai.App
import com.rhaegar.meipai.R
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:连接蓝牙ViewModel
 * Date: 2019/3/25
 */
class ConnectViewModel @Inject constructor(app: Application) :
    AndroidViewModel(app) {


    var connectState = MutableLiveData<String>()

    init {
        connectState.value= App.app.getString(R.string.connecting)
    }





}