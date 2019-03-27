package com.rhaegar.meipai.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rhaegar.meipai.R
import com.rhaegar.meipai.repository.BlueToothRepository
import com.rhaegar.meipai.repository.SendMessageRepository
import com.rhaegar.meipai.util.L
import javax.inject.Inject

/**
 * Author: Li Hai Kun
 * Description:
 * Date: 2019/3/25
 */
class MainViewModel @Inject constructor(val app: Application, private val blueToothRepository: BlueToothRepository,
                                        private val sendMessageRepository: SendMessageRepository
) :
    ViewModel(), LongClickButton2.CancelListener {


    private val index = MutableLiveData<Int>()//当前选择页面

    val title: LiveData<String> = Transformations.switchMap(index) {
        val data = MutableLiveData<String>()
        data.value = getTitleByIndex(it)
        return@switchMap data
    }//当前选择页面标题

    val startPositionValue = MutableLiveData<Int>()//起始位置设置状态 1 2 3 4 5
    val rotatePositionValue = MutableLiveData<Int>()//旋转位置设置状态 1 2 3 4 5

    val startPositionClickAble: LiveData<Boolean> = Transformations.switchMap(rotatePositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 1 || it == 5
        data
    }//滑轨起点是否可以设置

    val startPositionSureClickAble: LiveData<Boolean> = Transformations.switchMap(startPositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 2 || it == 4||it==6
        data
    }//滑轨起点确定是否可以点击

    val rotatePositionClickAble: LiveData<Boolean> = Transformations.switchMap(startPositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 1 || it == 5
        data
    }//旋转起点是否可以设置

    val rotatePositionSureClickAble: LiveData<Boolean> = Transformations.switchMap(rotatePositionValue) {
        val data = MutableLiveData<Boolean>()
        data.value = it == 2 || it == 4||it==6
        data
    }//旋转起点确定是否可以点击

    val startPositionText: LiveData<String> = Transformations.switchMap(startPositionValue) {
        val liveData = MutableLiveData<String>()
        when (it) {
            1 -> {
                liveData.value = app.getString(R.string.start_position_setting_1)
            }
            2,5,6 -> {
                liveData.value = app.getString(R.string.start_position_setting_a)
            }
            3,4 -> {
                liveData.value = app.getString(R.string.start_position_setting_b)
            }

        }
        liveData
    }//起始位置文字

    val rotatePositionText: LiveData<String> = Transformations.switchMap(rotatePositionValue) {
        val liveData = MutableLiveData<String>()
        when (it) {
            1 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_1)
            }
            2,5,6 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_a)
            }
            3,4 -> {
                liveData.value = app.getString(R.string.rotate_start_position_setting_b)
            }
        }
        liveData
    }//旋转位置文字


    val slideSpeed = MutableLiveData<Int>()//滑轨速度值
    val horizontalRotation = MutableLiveData<Int>()//水平旋转值

    val isTakeVideo = MutableLiveData<Boolean>()//是否正在拍摄


    val spaceTime = MutableLiveData<String>()//拍摄间隔
    val takeNumbers = MutableLiveData<String>()//拍摄张数
    val buttonTimes = MutableLiveData<String>()//按下快门时间

    private val finishNumbersValue = MutableLiveData<Int>()//完成张数

    private val finishTimesValue = MutableLiveData<Long>()//完成时间

    val finishNumbers: LiveData<String> = Transformations.switchMap(finishNumbersValue) {
        val liveData = MutableLiveData<String>()
        liveData.value = app.getString(R.string.finish_numbers_value, it, takeNumbers.value?.toInt() ?: 0)
        liveData
    }//完成张数


    var startTime = System.currentTimeMillis()

    val finishTimes: LiveData<String> = Transformations.switchMap(finishTimesValue) {
        val liveData = MutableLiveData<String>()
        val s = (it - startTime) / 1000
        liveData.value = app.getString(R.string.finish_time_value, s / 60, s % 60)
        liveData
    }//完成时间

    val percent: LiveData<Int> = Transformations.switchMap(finishNumbersValue) {
        val liveData = MutableLiveData<Int>()

        val value = ((it / (takeNumbers.value?.toFloat() ?: it.toFloat())) * 100).toInt()
        liveData.value = value
        return@switchMap liveData
    }


    val isTakeDelayVideo = MutableLiveData<Boolean>()//是否正在拍摄延迟


    init {
        index.value = 0
        startPositionValue.value = 1
        rotatePositionValue.value = 1
        slideSpeed.value = 40
        horizontalRotation.value = 60
        isTakeVideo.value = false
        isTakeDelayVideo.value = false


        spaceTime.value = "40"
        takeNumbers.value = "50"
        buttonTimes.value = "60"

        finishNumbersValue.value = 25
        finishTimesValue.value = 15000 + System.currentTimeMillis()

    }

    private fun uploadParm() {

    }


    private fun getTitleByIndex(index: Int): String {

        return when (index) {
            0 -> {
                app.getString(R.string.start_position)
            }
            1 -> {
                app.getString(R.string.vedio)
            }
            else -> {
                app.getString(R.string.delay)
            }
        }
    }

    fun changeIndex(position: Int) {
        index.value = position
    }

    fun startOrPauseTakeVideo() {
        isTakeVideo.value = isTakeVideo.value != true
    }

    fun startOrPauseTakeDelayVideo() {
        isTakeDelayVideo.value = isTakeDelayVideo.value != true
    }

    fun longClick(index: Int) {
        L.e("长按$index")
        when (index) {
            1, 2 -> {
                when (startPositionValue.value) {
                    1 -> {
                        startPositionValue.value = 2
                    }
                    2 -> {

                    }
                    3->{
                        startPositionValue.value = 4
                    }
                    4 -> {

                    }
                    5->{
                        startPositionValue.value = 6
                    }
                    6->{

                    }
                }

                if (index==1){
                    sendMessageRepository.sendRailLeftMove(true)
                }else{
                    sendMessageRepository.sendRailRightMove(true)
                }
            }
            3, 4 -> {
                when (rotatePositionValue.value) {
                    1 -> {
                        rotatePositionValue.value = 2
                    }
                    2 -> {

                    }
                    3 -> {
                        rotatePositionValue.value = 4
                    }
                    4->{

                    }
                    5->{
                        rotatePositionValue.value = 6
                    }
                    6->{

                    }
                }

                if (index==3){
                    sendMessageRepository.sendYawLeftMove(true)
                }else{
                    sendMessageRepository.sendYawRightMove(true)
                }
            }
        }
    }


    fun sure(index: Int) {
        L.e("确定$index")
        if (index == 1) {
            when (startPositionValue.value) {
                2,6 -> {
                    startPositionValue.value = 3
                }
                4 -> {
                    startPositionValue.value = 5
                }
            }
        } else {
            when (rotatePositionValue.value) {
                2,6 -> {
                    rotatePositionValue.value = 3
                }
                4 -> {
                    rotatePositionValue.value = 5
                }
            }
        }
    }

    fun cancel() {
        L.e("取消")

        startPositionValue.value=1
        rotatePositionValue.value=1
    }

    fun takePhoto() {
        L.e("拍照")
    }

    fun textToInt(it: String, liveData: MutableLiveData<String>): String {

        var text = it
        if (it.isEmpty()) {
            text = "1"
        } else if (it.startsWith("0")) {
            if (it.length > 1) {
                text = it.substring(1)
            } else {
                text = "1"
            }
        }

        if (liveData == spaceTime && spaceTime.value != text) {
            spaceTime.value = text
        } else if (liveData == takeNumbers && takeNumbers.value != text) {
            takeNumbers.value = text
        } else if (liveData == buttonTimes && buttonTimes.value != text) {
            buttonTimes.value = text
        }
        uploadParm()
        return text
    }


    override fun onCancel(arowId: Int) {
        L.e("取消长按$arowId")

    }

}