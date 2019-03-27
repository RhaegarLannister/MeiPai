package com.rhaegar.meipai.ui.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rhaegar.meipai.R
import com.rhaegar.meipai.repository.BlueToothRepository
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:连接蓝牙ViewModel
 * Date: 2019/3/25
 */
class ConnectViewModel @Inject constructor(app:Application, private val blueToothRepository: BlueToothRepository) : AndroidViewModel(app) {

    var connectState:MutableLiveData<String> = MutableLiveData()

    val scanBleList= blueToothRepository.search()

    init {
        connectState.value=app.getString(R.string.connecting)
    }

}